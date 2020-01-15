package com.ocft.gateway.stateMachineBak.buildState;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.mapper.BackonInterfaceMapper;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.stateMachineBak.state.ServiceTask;
import com.ocft.gateway.stateMachineBak.state.State;
import com.ocft.gateway.utils.StateMachineUtil;
import com.ocft.gateway.web.dto.FlowNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ocft.gateway.common.BizConstantPool.*;
import static com.ocft.gateway.common.BizConstantPool.SUCCEED;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/14 18:06
 * @Description:
 */
@Component
public class BuildServiceTask implements BuildState{

    @Autowired
    private BackonMapper backonMapper;

    @Autowired
    private BackonInterfaceMapper backonInterfaceMapper;


    @Override
    public State buildState(FlowNode sourceNode, FlowNode targetNode, FlowNode beforeStateName) {
        String stateType = sourceNode.getStateType();
        String stateName = StateMachineUtil.getEncodedStateName(sourceNode);

        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setType(stateType);
        List<Object> inputs = new ArrayList<>();
        Map<String, String> input = new HashMap<>();
        if (STATE_TYPE_CONVERTER.equals(stateType)) {
            serviceTask.setServiceName("paramsConverter");
            serviceTask.setServiceMethod("convert");

            String lastStateResult = beforeStateName + "_result";
            input.put("data", "$.[" + lastStateResult + "].data");
            input.put("context", "$.#root");
            input.put("current", stateName);
        } else if (STATE_TYPE_TASK.equals(stateType)){
            serviceTask.setServiceName("defaultInvokeOut");
            serviceTask.setServiceMethod("invokeHandler");

            if (StringUtils.isEmpty(StateMachineUtil.getEncodedStateName(beforeStateName))){
                input.put("requestData", "$.[" + stateName + "][requestData]");
            } else {
                input.put("requestData", "$.[" + beforeStateName + "][requestData]");
            }
            input.put("backOnUrl", "$.[" + stateName + "][backOnUrl]");
            BackonInterface backonInterface = backonInterfaceMapper.selectOne(new QueryWrapper<BackonInterface>().eq("url", sourceNode.getUrl()).eq("status", 1).eq("is_deleted", "0"));
            String system = backonInterface.getSystem();
            Backon backon = backonMapper.selectOne(new QueryWrapper<Backon>().eq("system", system).eq("status", "1").eq("is_deleted", "0"));
            String successCode = backon.getSuccessCode();
            String successValue = backon.getSuccessValue();
            input.put(successCode, "$.[" + stateName + "]["+successCode+"]");
            input.put(successValue, "$.[" + stateName + "]["+successValue+"]");
        }
        inputs.add(input);
        Map<String,Object> output = new HashMap<>();
        output.put(stateName + "_result", DEFAULT_RESULT_VALUE);

        if (STATE_MACHINE_FLOW_END.equals(targetNode.getId())){
            serviceTask.setNext(SUCCEED);
        } else {
            serviceTask.setNext(StateMachineUtil.getEncodedStateName(targetNode));
        }
        serviceTask.setInput(inputs);
        serviceTask.setOutput(output);
        return serviceTask;
    }
}
