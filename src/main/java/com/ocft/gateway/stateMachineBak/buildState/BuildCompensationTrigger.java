package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.CompensationTrigger;
import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.web.dto.FlowNode;
import static com.ocft.gateway.common.BizConstantPool.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 09:51
 * @Description:
 */
@Component
public class BuildCompensationTrigger implements BuildState{
    @Override
    public State buildState(FlowNode sourceNode, List<FlowNode> targetNodes, FlowNode beforeState) {
        CompensationTrigger compensationTrigger = new CompensationTrigger();
        compensationTrigger.setType(STATE_TYPE_COMPENSATIONTRIGGER);
        compensationTrigger.setNext(FAIL);
        return compensationTrigger;
    }
}
