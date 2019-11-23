package com.ocft.gateway.interceptor;

import com.ocft.gateway.common.context.GatewayContext;

/**
 * @author lijiaxing
 * @Title: GatewayInterceptor
 * @ProjectName gateway
 * @date 2019/11/23下午11:28
 * @Description: 网关拦截器接口
 */
public interface GatewayInterceptor {
    void doInterceptor(GatewayContext context);
}
