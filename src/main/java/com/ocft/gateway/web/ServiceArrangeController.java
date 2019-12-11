package com.ocft.gateway.web;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.converter.ServiceArrangeJsonConverter;
import com.ocft.gateway.web.dto.request.ConvertServiceArrangeRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lijiaxing
 * @Title: ServiceArrangeController
 * @ProjectName gateway
 * @date 2019/12/10下午5:52
 * @Description: TODO
 */
@RestController
@RequestMapping("/serviceArrange")
public class ServiceArrangeController {

    public Map<String, Object> getStateMachineConfig(ConvertServiceArrangeRequest req){
        //JSONObject converter = ServiceArrangeJsonConverter.converter(req.getTasks());
        return null;
    }
}
