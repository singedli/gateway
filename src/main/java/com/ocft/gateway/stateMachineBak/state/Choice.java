package com.ocft.gateway.stateMachineBak.state;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:24
 * @Description:
 */
@Data
@Accessors(chain = true)
public class Choice extends State {

    @JSONField(name = "Choices")
    private List<Map<String,Object>> choices;//可选的分支列表

    @JSONField(name = "Default")
    private String defaultValue;//默认值
}
