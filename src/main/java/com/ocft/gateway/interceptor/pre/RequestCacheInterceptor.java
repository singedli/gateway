package com.ocft.gateway.interceptor.pre;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.interceptor.GatewayInterceptor;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.utils.HttpUtil;
import com.ocft.gateway.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: RequestCacheInterceptor
 * @ProjectName gateway
 * @date 2019/11/23下午6:17
 * @Description: 请求缓存拦截器
 */
@Slf4j
@Component
public class RequestCacheInterceptor implements GatewayInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void doInterceptor(GatewayContext context) {
        throw new GatewayException(ResponseEnum.BACKON_NOT_EXIST);
//        System.out.println("请求缓存拦截器被执行！");
//        //把请求参数转换为“KEY_VALUE”的字符串
//        String[] requestParams = context.getRequestBody().split(",");
//        StringBuilder params = new StringBuilder();
//        for (String requestParam : requestParams) {
//            params.append(requestParam).append("_");
//        }
//
//        if(context.getCacheStatus() == 0) {
//            //before
//            String result = (String) redisUtil.hget(context.getGatewayInterface().getUrl(), (String) params.subSequence(0, params.length() - 1));
//            if (result != null) {
//                context.setCacheData(result);
//            }
//        }else {
//            //after 已缓存的不需要执行这一步
//            redisUtil.hset(context.getGatewayInterface().getUrl(), (String) params.subSequence(0, params.length() - 1),context.getCacheData());
//        }
    }
}
