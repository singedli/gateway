package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.task.ConcurrentInvokeTask;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.spring.SpringContextHolder;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author lijiaxing
 * @Title: GatewayContextConverter
 * @ProjectName gateway
 * @date 2019/11/24下午3:40
 * @Description: TODO
 */
public class GatewayContextConverter {

    public static List<ConcurrentInvokeTask> convert(GatewayContext gatewayContext, CountDownLatch latch){
        List<ConcurrentInvokeTask> taskList = new ArrayList<>();
        GatewayInterface gatewayInterface = gatewayContext.getGatewayInterface();
        String backonUrls = gatewayInterface.getBackonUrl();
        for (String backonUrl : backonUrls.split(",")) {
            IBackonInterfaceService backonInterfaceService = SpringContextHolder.getBean(IBackonInterfaceService.class);
            BackonInterface backonInterface = backonInterfaceService.getBackonInterface(backonUrl);
            ConcurrentInvokeTask concurrentInvokeTask = new ConcurrentInvokeTask()
                    .setHeader(backonInterface.getHttpHeader())
                    .setHttpMethod(backonInterface.getHttpMethod())
                    .setLatch(latch)
                    .setRequestBody(gatewayContext.getRequestBody());
            taskList.add(concurrentInvokeTask);
        }
        Assert.notEmpty(taskList,ResponseEnum.BACKON_INTERFACE_NOT_EXIST.getMessage());
        return taskList;
    }

    //把请求body参数转换为“key1_vlaue1_key2_value2_...”的字符串
    public static String convertRedisHashField(GatewayContext gatewayContext){
        String requestBody = JSONObject.toJSONString(JSONObject.parseObject(gatewayContext.getRequestBody()));
        //获取网关接口缓存配置的Requestbody
        String keys = gatewayContext.getGatewayCache().getRequestbody();
        //将键值对分割
        String[] requestParams = requestBody.substring(1,requestBody.length()-1).split(",");
        StringBuilder params = new StringBuilder();

        for (String requestParam : requestParams) {
            String substring = null;
            if(requestParam.endsWith("\"")){
                substring = requestParam.substring(1, requestParam.length() - 1);
                //判断是否为配置的key
                if(keys.contains(substring.split("\":\"")[0])){
                    //拼接每组键值对为key_value的形式
                    params.append(substring.split("\":\"")[0]).append("_").append(substring.split("\":\"")[1]).append("_");
                }
            }else {
                substring = requestParam.substring(1, requestParam.length());
                //判断是否为配置的key
                if(keys.contains(substring.split("\":\"")[0])){
                    //拼接每组键值对为key_value的形式
                    params.append(substring.split("\":")[0]).append("_").append(substring.split("\":")[1]).append("_");
                }
            }
        }
        String field = String.valueOf(params);
        return field.substring(0,field.length()-1);
    }
}
