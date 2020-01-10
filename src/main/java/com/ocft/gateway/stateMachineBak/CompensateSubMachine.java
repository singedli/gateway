package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:47
 * @Description:
 */
@Data
@Accessors(chain = true)
public class CompensateSubMachine extends State {

    @JSONField(name = "ParameterTypes")
    private List<String> parameterTypes;//参数类型

    @JSONField(name = "Input")
    private List<Object> input;//输入参数列表
}
