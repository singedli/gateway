package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:37
 * @Description:
 */
@Data
@Accessors(chain = true)
public class CompensationTrigger extends State {

    @JSONField(name = "Next")
    private String next;//下一个执行的"状态"
}
