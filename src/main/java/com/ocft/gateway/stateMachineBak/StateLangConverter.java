package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.mapper.BackonInterfaceMapper;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.stateMachineBak.state.BuildState;
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
    private BuildState buildState;

    public  String convert(FlowStateLangRequest req) {
        //生成StateMachine对象
        StateMachine stateMachine = buildStateMachine(req);
        Map<String, State> states = new HashMap<>();
        List<FlowEdge> edges = req.getEdges();

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

            String stateType = null;

            if(STATE_TYPE_TASK.equals(sourceNode.getStateType())||STATE_TYPE_CONVERTER.equals(sourceNode.getStateType())){
                stateType = STATE_DEFAULT_TYPE;
            } else {
                stateType = sourceNode.getStateType();
            }

            //生成State对象
            State state =null;
            switch(stateType){
                case "ServiceTask" :
                    state = buildState.buildServiceTask(sourceNode,targetStateName,sourceStateName,beforeStateName,targetId);
                    beforeStateName = sourceStateName;
                    break;
                case "CompensationTrigger" :
                    state = buildState.buildCompensationTrigger(sourceNode);
                    break;
                case "Choice" :
                    state = buildState.buildChoice(sourceNode);
                    break;
                case "Succeed" :
                    state = buildState.buildSucceed(sourceNode);
                    break;
                case "Fail" :
                    state = buildState.buildFail(sourceNode);
                    break;
                case "SubStateMachine" :
                    state = buildState.buildSubStateMachine(sourceNode);
                    break;
                case "CompensateSubMachine" :
                    state = buildState.buildCompensateSubMachine(sourceNode);
                    break;
            }

            states.put(sourceStateName, state);

            if (STATE_MACHINE_FLOW_END.equals(targetId)) {
                targetNode.setStateType(SUCCEED);
                State endState = buildState.buildSucceed(targetNode);
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
