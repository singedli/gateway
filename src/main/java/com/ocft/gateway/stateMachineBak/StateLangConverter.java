package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.mapper.BackonInterfaceMapper;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.utils.StateMachineUtil;
import com.ocft.gateway.web.dto.FlowEdge;
import com.ocft.gateway.web.dto.FlowNode;
import com.ocft.gateway.web.dto.request.FlowStateLangRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.ocft.gateway.common.BizConstantPool.*;
import java.util.*;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:32
 * @Description:
 */
@Component
public class StateLangConverter {

    @Autowired
    private BackonMapper backonMapper;

    @Autowired
    private BackonInterfaceMapper backonInterfaceMapper;

    public  String convert(FlowStateLangRequest req) {
        //生成StateMachine对象
        StateMachine stateMachine = buildStateMachine(req);
        Map<String, State> states = new HashMap<>();
        List<FlowEdge> edges = req.getEdges();
        System.out.println(edges);
        //前一个状态
        String beforeStateName = null;
        for (FlowEdge edge: edges) {
            String sourceId = edge.getSource();
            String targetId = edge.getTarget();
            FlowNode sourceNode = getFlowNodeById(req, sourceId);
            FlowNode targetNode = getFlowNodeById(req, targetId);

            if (STATE_MACHINE_FLOW_START.equals(sourceId)) {
                String startState = StateMachineUtil.getEncodedStateName(targetNode);
                stateMachine.setStartState(startState);
                continue;
            }

            String targetStateName = StateMachineUtil.getEncodedStateName(targetNode);
            String sourceStateName = StateMachineUtil.getEncodedStateName(sourceNode);

            //生成State对象
            State state = buildState(sourceNode,targetStateName,sourceStateName,beforeStateName,targetId);
            beforeStateName = sourceStateName;
//            buildStateOutput(sourceNode,state);
            states.put(sourceStateName, state);

            if(STATE_MACHINE_FLOW_END.equals(targetId)){
                targetNode.setStateType(SUCCEED);
                State endState = buildState(targetNode,null,null,null,null);
                states.put(SUCCEED, endState);
            }
        }
        stateMachine.setStates(states);
        return JSONObject.toJSONString(stateMachine);
    }

    /**
     * 构建状态机
     *
     * @param req
     * @return
     */
    public static StateMachine buildStateMachine(FlowStateLangRequest req) {
        StateMachine stateMachine = new StateMachine();
        GatewayInterface gatewayInterface = req.getGatewayInterface();
        String url = gatewayInterface.getUrl();
        String stateName = url + STATE_DEFAULT_SEPARATOR + STATE_MACHINE;
        String gatewayInterfaceName = gatewayInterface.getName();
        stateMachine.setName(StateMachineUtil.safeEncode(stateName));
        stateMachine.setComment(STATE_MACHINE_COMMENT_PREFIX + gatewayInterfaceName + STATE_MACHINE_COMMENT_SUFFIX);
        stateMachine.setVersion(STATE_MACHINE_DEFAULT_VERSION);
        return stateMachine;
    }

    /**
     * 构建状态机中的子状态
     * @param flowNode
     * @return
     */
    public  State buildState(FlowNode flowNode,String next,String stateName,String beforeStateName,String targetId) {
        String stateType = null;

        if(STATE_TYPE_TASK.equals(flowNode.getStateType())||STATE_TYPE_CONVERTER.equals(flowNode.getStateType())){
            stateType = STATE_DEFAULT_TYPE;
        } else {
            stateType = flowNode.getStateType();
        }

        State state = null;
        switch(stateType){
            case "ServiceTask" :
                state = new ServiceTask();
                state.setType(stateType);
                List<Object> inputs = new ArrayList<>();
                Map<String, String> input = new HashMap<>();
                if(STATE_TYPE_CONVERTER.equals(flowNode.getStateType())){
                    ((ServiceTask) state).setServiceName("paramsConverter");
                    ((ServiceTask) state).setServiceMethod("convert");

                    String lastStateResult = beforeStateName + "_result";
                    input.put("data", "$.[" + lastStateResult + "].data");
                    input.put("context", "$.#root");
                    input.put("current", stateName);
                } else if(STATE_TYPE_TASK.equals(flowNode.getStateType())){
                    ((ServiceTask) state).setServiceName("defaultInvokeOut");
                    ((ServiceTask) state).setServiceMethod("invokeHandler");

                    if(StringUtils.isEmpty(beforeStateName)){
                        input.put("requestData", "$.[" + stateName + "][requestData]");
                    }else {
                        input.put("requestData", "$.[" + beforeStateName + "][requestData]");
                    }
                    input.put("backOnUrl", "$.[" + stateName + "][backOnUrl]");
                    BackonInterface backonInterface = backonInterfaceMapper.selectOne(new QueryWrapper<BackonInterface>().eq("url", flowNode.getUrl()).eq("status", 1).eq("is_deleted", "0"));
                    String system = backonInterface.getSystem();
                    Backon backon = backonMapper.selectOne(new QueryWrapper<Backon>().eq("system", system).eq("status", "1").eq("is_deleted", "0"));
                    String successCode = backon.getSuccessCode();
                    String successValue = backon.getSuccessValue();
                    input.put(successCode, "$.[" + stateName + "]["+successCode+"]");
                    input.put(successValue, "$.[" + stateName + "]["+successValue+"]");
                }
                inputs.add(input);
                Map<String,Object> output = new HashMap<>();
                output.put(stateName + "_result", DEFAULT_RESULT_VALUE);

                if(STATE_MACHINE_FLOW_END.equals(targetId)){
                    ((ServiceTask) state).setNext(SUCCEED);
                }else {
                    ((ServiceTask) state).setNext(next);
                }
                ((ServiceTask) state).setInput(inputs);
                ((ServiceTask) state).setOutput(output);
                break;
            case "Choice" :
                state =  new Choice();
                state.setType(stateType);
                ((Choice) state).setDefaultValue("Fail");
                //TODO
            case "CompensationTrigger" :
                state = new CompensationTrigger();
                state.setType(stateType);
                ((CompensationTrigger) state).setNext("Fail");
                //TODO
                break;
            case "Succeed" :
                state = new Succeed();
                state.setType(stateType);
                break;
            case "Fail" :
                state = new Fail();
                state.setType(stateType);
                ((Fail) state).setMessage("purchase failed");
                ((Fail) state).setErrorCode("PURCHASE_FAILED");
                break;
            case "SubStateMachine" :
                state = new SubStateMachine();
                state.setType(stateType);
                ((CompensationTrigger) state).setNext("Succeed");
                //TODO
                break;
            case "CompensateSubMachine" :
                state = new CompensateSubMachine();
                state.setType(stateType);
                //TODO
                break;
        }
        return state;
    }

    /**
     *  根据ID从node数组中查找对应的node
     * @param req
     * @param id
     * @return
     */
    private static FlowNode getFlowNodeById(FlowStateLangRequest req, String id) {
        List<FlowNode> nodes = req.getNodes();
        FlowNode flowNode = nodes.stream().filter(n -> id.equals(n.getId())).findFirst().get();
        Assert.notNull(flowNode, "流程图中不存在id为" + id + "的节点");
        return flowNode;
    }

}
