package com.ocft.gateway.web;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.out.impl.DefaultInvokeOutImpl;
import com.ocft.gateway.web.dto.InvokeThirdDTO;
import com.ocft.gateway.web.response.HttpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author: Bobby
 * @create: 2019-12-06 17:22
 * @description: sao测试
 **/
@Controller
@RequestMapping("/saoTest")
public class SaoTestController {


    @Autowired
    DefaultInvokeOutImpl defaultInvokeOut;

    @RequestMapping("/concurrentTest")
    @ResponseBody
    public HttpResponseModel<Object> test(@RequestBody Map<String, String> map) {
        InvokeThirdDTO dto = new InvokeThirdDTO();
        dto.setRequestData(JSONObject.toJSONString(map));
        dto.setBackOnUrl("https://portal-test.medsci.cn/top/weekTop");
        HttpResponseModel<Object> responseModel = defaultInvokeOut.invokeHandler(dto);
        return responseModel;
    }

}
