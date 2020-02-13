package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.CompensateSubMachine;
import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.web.dto.FlowNode;
import static com.ocft.gateway.common.BizConstantPool.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 09:58
 * @Description:
 */
@Component
public class BuildCompensateSubMachine implements BuildState {
    @Override
    public State buildState(FlowNode sourceNode, List<FlowNode> targetNodes, FlowNode beforeState) {
        CompensateSubMachine compensateSubMachine = new CompensateSubMachine();
        compensateSubMachine.setType(STATE_TYPE_COMPENSATESUBMACHINE);
        return compensateSubMachine;
    }
}
