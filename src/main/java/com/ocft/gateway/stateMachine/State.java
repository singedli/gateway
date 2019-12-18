package com.ocft.gateway.stateMachine;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: State
 * @ProjectName gateway
 * @date 2019/12/13上午9:36
 * @Description: TODO
 */
@Data
public class State {
    @JSONField(name = "Type")
    private String Type;
    @JSONField(name = "ServiceName")
    private String ServiceName;
    @JSONField(name = "ServiceMethod")
    private String ServiceMethod;
    @JSONField(name = "Next")
    private String Next;
    @JSONField(name = "Input")
    private List<Map<String,String>> Input;
    @JSONField(name = "Output")
    private Map<String,String> Output;
}
