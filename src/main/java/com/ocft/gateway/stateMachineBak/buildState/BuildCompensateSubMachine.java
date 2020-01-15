package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.web.dto.FlowNode;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 09:58
 * @Description:
 */
@Component
public class BuildCompensateSubMachine implements BuildState {
    @Override
    public State buildState(FlowNode sourceNode, FlowNode targetNode, FlowNode beforeStateName) {
        return null;
    }
}
