package com.ocft.gateway.interceptor.pre;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.interceptor.GatewayInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    @Override
    public void doInterceptor(GatewayContext context) {
        System.out.println("请求缓存拦截器被执行！");
    }
}
