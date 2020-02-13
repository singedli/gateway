package com.ocft.gateway.common;

/**
 * @author lijiaxing
 * @Title: BizConstantPool
 * @ProjectName gateway
 * @date 2019/12/26下午4:41
 * @Description: 常量池
 */
public class BizConstantPool {
    public static final String STATE_DEFAULT_SEPARATOR = "#";
    public static final String STATE_MACHINE = "stateMachine";
    public static final String STATE_MACHINE_COMMENT_PREFIX = "接口";
    public static final String STATE_MACHINE_COMMENT_SUFFIX = "的状态机";
    public static final String STATE_MACHINE_DEFAULT_VERSION = "0.0.2";
    public static final String STATE_MACHINE_FLOW_START = "0000000000";
    public static final String STATE_MACHINE_FLOW_END = "1111111111";
    public static final String STATE_DEFAULT_TYPE = "ServiceTask";
    public static final String STATE_TYPE_CONVERTER = "converter";
    public static final String STATE_TYPE_TASK = "task";
    public static final String STATE_TYPE_CHOICE = "Choice";
    public static final String STATE_TYPE_COMPENSATESTATE = "compensate";
    public static final String STATE_TYPE_COMPENSATIONTRIGGER = "CompensationTrigger";
    public static final String STATE_TYPE_SUBSTATEMACHINE = "SubStateMachine";
    public static final String STATE_TYPE_COMPENSATESUBMACHINE = "CompensateSubMachine";
    public static final String DEFAULT_RESULT = "result";
    public static final String DEFAULT_RESULT_VALUE = "$.#root";
    public static final String STATE_EXCEPTION_NAME = "java.lang.Throwable";
    public static final String STATE_EXCEPTION_KEY = "Exceptions";
    public static final String Next = "Next";
    public static final String FAIL = "Fail";
    public static final String SUCCEED = "Succeed";
}
