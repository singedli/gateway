package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.CompensationTrigger;
import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.web.dto.FlowNode;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 09:51
 * @Description:
 */
@Component
public class BuildCompensationTrigger implements BuildState{
    @Override
    public State buildState(FlowNode sourceNode, FlowNode targetNode, FlowNode beforeStateName) {
        CompensationTrigger compensationTrigger = new CompensationTrigger();
        compensationTrigger.setType(sourceNode.getStateType());
        compensationTrigger.setNext("Fail");
        return compensationTrigger;
    }
}
