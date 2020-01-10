package com.ocft.gateway.enums;

import lombok.Getter;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/8 10:35
 * @Description:
 */
@Getter
public enum StateMachineType {
    SERVICE_TASK("ServiceTask"),
    CHOICE("Choice"),
    COMPENSATION_TRIGGER("CompensationTrigger"),
    SUCCEED("Succeed"),
    FAIL("Fail"),
    SUB_STATE_MACHINE("SubStateMachine"),
    COMPENSATE_SUB_MACHINE("CompensateSubMachine");

    private String typeName;

    private StateMachineType(String typeName){
        this.typeName = typeName;
    }
}
