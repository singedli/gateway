package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:25
 * @Description:
 */
@Data
@Accessors(chain = true)
public class State {

    @JSONField(name = "Type")
    private String type;//状态类型
}
