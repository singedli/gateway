package com.ocft.gateway.interceptor.pre;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.interceptor.GatewayInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lijiaxing
 * @Title: RequestLimitIntercept
 * @ProjectName gateway
 * @date 2019/11/23下午5:50
 * @Description: 接口防刷拦截器
 */

//todo 1恶意调用策率包  2用户账号密码


@Slf4j
@Component
public class RequestLimitIntercept implements GatewayInterceptor {
    @Override
    public void doInterceptor(GatewayContext context) {

    }
}
