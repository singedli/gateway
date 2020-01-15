package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.annotation.JSONField;
import com.ocft.gateway.stateMachineBak.state.State;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class StateMachine {

    @JSONField(name = "Name")
    private String name;

    @JSONField(name = "Comment")
    private String comment;

    @JSONField(name = "StartState")
    private String startState;

    @JSONField(name = "Version")
    private String version;

    @JSONField(name = "States")
    private Map<String,State> states = new HashMap<>();

}