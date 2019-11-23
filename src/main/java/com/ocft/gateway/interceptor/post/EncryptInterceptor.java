package com.ocft.gateway.interceptor.post;

import com.ocft.gateway.interceptor.GatewayInterceptor;
import com.ocft.gateway.common.context.GatewayContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author lijiaxing
 * @Title: EncryptInterceptor
 * @ProjectName gateway
 * @date 2019/11/23下午5:09
 * @Description: 加密拦截器
 */
@Slf4j
@Component
public class EncryptInterceptor implements GatewayInterceptor {

    @Override
    public void doInterceptor(GatewayContext context) {
        System.err.println("加密拦截器被调用！");
    }
}
