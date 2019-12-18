package com.ocft.gateway.stateMachine;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: StateMachine
 * @ProjectName gateway
 * @date 2019/12/13上午9:34
 * @Description: TODO
 */
@Data
public class StateMachine {
    @JSONField(name = "Name")
    private String Name;
    @JSONField(name = "Comment")
    private String Comment;
    @JSONField(name = "StartState")
    private String StartState;
    @JSONField(name = "Version")
    private String Version;
    @JSONField(name = "States")
    private Map<String,State> States = new HashMap<>();
//    @JSONField(name = "Succeed")
//    private Map<String,String> Succeed = new HashMap<>();
}
