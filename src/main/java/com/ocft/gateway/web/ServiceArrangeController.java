package com.ocft.gateway.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.spring.SpringContextHolder;
import com.ocft.gateway.stateMachine.StateLangGenerator;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.dto.request.ConvertServiceArrangeRequest;
import io.seata.saga.engine.StateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private IGatewayInterfaceService gatewayInterfaceService;

    @Autowired
    private StateMachineEngine stateMachineEngine;

    @RequestMapping("/addServiceArrange")
    public Map<String, Object> getStateMachineConfig(@RequestBody String data){
        StateLangGenerator generator = SpringContextHolder.getBean("stateLangGenerator");
        String generate = generator.generate(data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        GatewayInterface gatewayInterface = jsonObject.getObject("gatewayInterface", GatewayInterface.class);
        JSONArray nodes = jsonObject.getJSONArray("nodes");
        List<JSONObject>  backonAndUrlList= new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            JSONObject node = nodes.getJSONObject(i);
            if("task".equalsIgnoreCase(node.getString("stateType"))){
                JSONObject backonAndUrl = new JSONObject();
                String url = node.getString("url");
                String system = node.getString("system");
                backonAndUrl.put("system",system);
                backonAndUrl.put("backonUrl",url);
                backonAndUrlList.add(backonAndUrl);
            }
        }
        gatewayInterface.setBackonUrl(JSONArray.toJSONString(backonAndUrlList));
        gatewayInterface.setInvokeConfig(generate);
        jsonObject.remove("gatewayInterface");
        gatewayInterface.setFlowConfig(jsonObject.toJSONString());
        gatewayInterfaceService.updateById(gatewayInterface);
        List<Resource> resourceList = new ArrayList<>();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(generate.getBytes());
        Resource resource = new InputStreamResource(inputStream);
        resourceList.add(resource);
        Resource[] resources = resourceList.toArray(new Resource[resourceList.size()]);
        try {
            stateMachineEngine.getStateMachineConfig().getStateMachineRepository().registryByResources(resources,"000001");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.successResult();
    }
}
