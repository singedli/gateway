package com.ocft.gateway.stateMachineBak.buildState;

import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.stateMachineBak.state.Succeed;
import com.ocft.gateway.web.dto.FlowNode;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ocft.gateway.common.BizConstantPool.*;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/15 09:50
 * @Description:
 */
@Component
public class BuildSucceed implements BuildState {
    @Override
    public State buildState(FlowNode sourceNode, List<FlowNode> targetNodes, FlowNode beforeState) {
        Succeed succeed = new Succeed();
        succeed.setType(SUCCEED);
        return succeed;
    }
}
