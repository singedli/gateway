package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.web.dto.FlowNode;

import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 10:50
 * @Description:
 */
public interface BuildState {
    State buildState(FlowNode sourceNode, List<FlowNode> targetNodes, FlowNode beforeState);
}
