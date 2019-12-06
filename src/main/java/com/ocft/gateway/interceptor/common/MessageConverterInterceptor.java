package com.ocft.gateway.interceptor.common;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.converter.JsonStructureConverter;
import com.ocft.gateway.entity.MessageConverter;
import com.ocft.gateway.interceptor.AbstractGatewayInterceptor;
import com.ocft.gateway.service.IMessageConverterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijiaxing
 * @Title: MessageStructInterceptor
 * @ProjectName gateway
 * @date 2019/12/6上午11:16
 * @Description: 报文内容字段转换拦截器
 */
@Slf4j
@Component
public class MessageConverterInterceptor extends AbstractGatewayInterceptor {

    @Autowired
    private IMessageConverterService messageConverterService;

    @Override
    public void doInterceptor(GatewayContext context) {
        MessageConverter messageConverter = messageConverterService.getMessageConverter(context.getGatewayInterface().getUrl());
        if(StringUtils.isEmpty(context.getResponseBody())){
            JSONObject requestBody = JsonStructureConverter.convertStructure(context.getRequestBody(), messageConverter.getRequestStruct(), messageConverter.getRequestConfig());
            context.setRequestBody(JSONObject.toJSONString(requestBody));
        }else {
            JSONObject responseBody = JsonStructureConverter.convertStructure(context.getResponseBody(), messageConverter.getResponseStruct(), messageConverter.getResponseConfig());
            returnResult(JSONObject.toJSONString(responseBody));
        }
    }
}
