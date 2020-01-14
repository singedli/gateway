package com.ocft.gateway.stateMachineBak.state;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.mapper.BackonInterfaceMapper;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.stateMachineBak.*;
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
 * @Date: 2020/1/14 17:13
 * @Description:
 */
@Component
public class BuildState {

    @Autowired
    private BackonMapper backonMapper;

    @Autowired
    private BackonInterfaceMapper backonInterfaceMapper;

    public State buildServiceTask(FlowNode flowNode, String next, String stateName, String beforeStateName, String targetId) {
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setType(flowNode.getStateType());
        List<Object> inputs = new ArrayList<>();
        Map<String, String> input = new HashMap<>();
        if (STATE_TYPE_CONVERTER.equals(flowNode.getStateType())) {
            serviceTask.setServiceName("paramsConverter");
            serviceTask.setServiceMethod("convert");

            String lastStateResult = beforeStateName + "_result";
            input.put("data", "$.[" + lastStateResult + "].data");
            input.put("context", "$.#root");
            input.put("current", stateName);
        } else if (STATE_TYPE_TASK.equals(flowNode.getStateType())){
            serviceTask.setServiceName("defaultInvokeOut");
            serviceTask.setServiceMethod("invokeHandler");

            if (StringUtils.isEmpty(beforeStateName)){
                input.put("requestData", "$.[" + stateName + "][requestData]");
            } else {
                input.put("requestData", "$.[" + beforeStateName + "][requestData]");
            }
            input.put("backOnUrl", "$.[" + stateName + "][backOnUrl]");
            BackonInterface backonInterface = backonInterfaceMapper.selectOne(new QueryWrapper<BackonInterface>().eq("url", flowNode.getUrl()).eq("status", 1).eq("is_deleted", "0"));
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

        if (STATE_MACHINE_FLOW_END.equals(targetId)){
            serviceTask.setNext(SUCCEED);
        } else {
            serviceTask.setNext(next);
        }
        serviceTask.setInput(inputs);
        serviceTask.setOutput(output);
        return serviceTask;
    }

    public State buildChoice(FlowNode flowNode){
        Choice choice = new Choice();
        choice.setType(flowNode.getStateType());
        choice.setDefaultValue("Fail");

        //TODO
        return choice;
    }

    public State buildCompensationTrigger(FlowNode flowNode){
        CompensationTrigger compensationTrigger = new CompensationTrigger();
        compensationTrigger.setType(flowNode.getStateType());
        compensationTrigger.setNext("Fail");
        return compensationTrigger;
    }

    public State buildSucceed(FlowNode flowNode) {
        Succeed succeed = new Succeed();
        succeed.setType(flowNode.getStateType());
        return succeed;
    }

    public State buildFail(FlowNode flowNode) {
        Fail fail = new Fail();
        fail.setType(flowNode.getStateType());
        fail.setMessage("purchase failed");
        fail.setErrorCode("PURCHASE_FAILED");
        return fail;
    }

    public State buildSubStateMachine(FlowNode flowNode) {
        SubStateMachine subStateMachine = new SubStateMachine();
        subStateMachine.setType(flowNode.getStateType());
        subStateMachine.setNext("Succeed");
        //TODO
        return subStateMachine;
    }

    public State buildCompensateSubMachine(FlowNode flowNode) {
        CompensateSubMachine compensateSubMachine = new CompensateSubMachine();
        compensateSubMachine.setType(flowNode.getStateType());
        //TODO
        return compensateSubMachine;
    }
}
