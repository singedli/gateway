package com.ocft.gateway.stateMachineBak.state;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:26
 * @Description:
 */
@Data
@Accessors(chain = true)
public class Fail extends State{

    @JSONField(name = "ErrorCode")
    private String errorCode;//Fail类型"状态"的错误码

    @JSONField(name = "Message")
    private String message;//Fail类型"状态"的错误信息
}
