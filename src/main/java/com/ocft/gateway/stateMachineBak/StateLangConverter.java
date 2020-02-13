package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.spring.SpringContextHolder;
import com.ocft.gateway.stateMachineBak.buildState.*;
import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.utils.StateMachineUtil;
import com.ocft.gateway.web.dto.FlowEdge;
import com.ocft.gateway.web.dto.FlowNode;
import com.ocft.gateway.web.dto.request.FlowStateLangRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.ocft.gateway.common.BizConstantPool.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:32
 * @Description:
 */
@Component
public class StateLangConverter {

    public  String convert(FlowStateLangRequest req) {
        //生成StateMachine对象
        StateMachine stateMachine = buildStateMachine(req);
        Map<String, State> states = new HashMap<>();
        List<FlowEdge> edges = req.getEdges();
        List<FlowNode> nodes = req.getNodes();

        //前一个状态
        FlowNode beforeNode = null;
        for (FlowNode node: nodes) {
            String nodeId = node.getId();

            //开始节点跳过
            if(STATE_MACHINE_FLOW_START.equals(nodeId)) continue;

            //状态名称
            String stateName = null;
            if(STATE_MACHINE_FLOW_END.equals(nodeId)) {
                stateName = SUCCEED;
            }else {
                stateName = StateMachineUtil.getEncodedStateName(node);
            }

            List<FlowNode> targetNodes = new ArrayList<>();

            //如果不是补偿节点和结束节点则需要确定下一个节点的集合
            if( !STATE_TYPE_COMPENSATESTATE.equals(node.getStateType()) ||
                    !STATE_MACHINE_FLOW_END.equals(nodeId)){
                List<FlowEdge> flowEdgeOfNode= edges.stream().filter( edge -> nodeId.equals(edge.getSource())).collect(Collectors.toList());
                for(FlowEdge flowEdge : flowEdgeOfNode){
                    FlowNode targetNode = nodes.stream().filter( n -> flowEdge.getTarget().equals(n.getId())).findFirst().get();
                    targetNodes.add(targetNode);
                }
            }

            //确定类型
            String stateType = null;
            if(STATE_TYPE_TASK.equals(node.getStateType())||
                    STATE_TYPE_CONVERTER.equals(node.getStateType())||
                    STATE_TYPE_COMPENSATESTATE.equals(node.getStateType())){
                stateType = STATE_DEFAULT_TYPE;
            } else {
                stateType = node.getStateType();
            }

            //生成State对象
            State state =null;
            switch(stateType){
                case "ServiceTask" :
                    BuildState serviceTask = SpringContextHolder.getBean(BuildServiceTask.class);
                    state = serviceTask.buildState(node,targetNodes,beforeNode);
                    beforeNode = node;
                    break;
                case "CompensationTrigger" :
                    BuildState compensationTrigger = SpringContextHolder.getBean(BuildCompensationTrigger.class);
                    state = compensationTrigger.buildState(node,targetNodes,beforeNode);
                    break;
                case "Choice" :
                    BuildState choice  = SpringContextHolder.getBean(BuildChoice.class);
                    state = choice.buildState(node,targetNodes,beforeNode);
                    break;
                case "Succeed" :
                    BuildState succeed = SpringContextHolder.getBean(BuildSucceed.class);
                    state = succeed.buildState(node,targetNodes,beforeNode);
                    break;
                case "Fail" :
                    BuildState fail = SpringContextHolder.getBean(BuildFail.class);
                    state = fail.buildState(node,targetNodes,beforeNode);
                    break;
                case "SubStateMachine" :
                    BuildState subStateMachine = SpringContextHolder.getBean(BuildSubStateMachine.class);
                    state = subStateMachine.buildState(node,targetNodes,beforeNode);
                    break;
                case "CompensateSubMachine" :
                    BuildState compensateSubMachine = SpringContextHolder.getBean(BuildCompensateSubMachine.class);
                    state = compensateSubMachine.buildState(node,targetNodes,beforeNode);
                    break;
            }

            states.put(stateName, state);
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
