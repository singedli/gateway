package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.stateMachineBak.state.SubStateMachine;
import com.ocft.gateway.web.dto.FlowNode;
import org.springframework.stereotype.Component;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 10:04
 * @Description:
 */
@Component
public class BuildSubStateMachine implements BuildState{
    @Override
    public State buildState(FlowNode sourceNode, FlowNode targetNode, FlowNode beforeStateName) {
        SubStateMachine subStateMachine = new SubStateMachine();
        subStateMachine.setType(sourceNode.getStateType());
        subStateMachine.setNext("Succeed");
        //TODO
        return subStateMachine;
    }
}
