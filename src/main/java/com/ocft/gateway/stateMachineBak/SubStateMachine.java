package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:44
 * @Description:
 */
@Data
@Accessors(chain = true)
public class SubStateMachine extends State {

    @JSONField(name = "StateMachineName")
    private String stateMachineName;//服务方法名

    @JSONField(name = "CompensateState")
    private String compensateState;//补偿状态

    @JSONField(name = "ParameterTypes")
    private List<String> parameterTypes;//参数类型

    @JSONField(name = "Input")
    private List<Object> input;//输入参数列表

    @JSONField(name = "Output")
    private Map<String,Object> output;//服务返回的参数

    @JSONField(name = "Next")
    private String next;//下一个执行的"状态"
}
