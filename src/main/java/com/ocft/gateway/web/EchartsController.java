package com.ocft.gateway.web;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Bobby
 * @create: 2019-12-11 09:35
 * @description: echarts动态渲染测试接口
 **/

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    private static final Logger logger = LoggerFactory.getLogger(EchartsController.class);

    @RequestMapping("/memory")
    public Map<String, Object> checkMemory() {
        Map<String, Object> map = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory() / 1024 / 1024;
        long usedMemory = runtime.totalMemory();
        map.put("freeMemory", freeMemory);
        map.put("usedMemory", usedMemory);
        logger.info(JSONObject.toJSONString(map));
        return ResultUtil.createResult(map);
    }

}
