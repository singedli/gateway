package com.ocft.gateway.interceptor.pre;

import com.ocft.gateway.interceptor.GatewayInterceptor;
import com.ocft.gateway.common.context.GatewayContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lijiaxing
 * @Title: DecryptInterceptor
 * @ProjectName gateway
 * @date 2019/11/23下午5:30
 * @Description: 解密拦截器
 */
@Slf4j
@Component
public class DecryptInterceptor implements GatewayInterceptor {

    @Override
    public void doInterceptor(GatewayContext context) {
        System.err.println("解密拦截器被调用");
    }
}
