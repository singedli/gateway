package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.Choice;
import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.web.dto.FlowNode;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ocft.gateway.common.BizConstantPool.*;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 09:51
 * @Description:
 */
@Component
public class BuildChoice implements BuildState{
    @Override
    public State buildState(FlowNode sourceNode, List<FlowNode> targetNodes, FlowNode beforeState) {
        Choice choice = new Choice();
        choice.setType(STATE_TYPE_CHOICE);
        choice.setDefaultValue(FAIL);

        //TODO
        return choice;
    }
}
