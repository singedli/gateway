package com.ocft.gateway.interceptor;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author lijiaxing
 * @Title: InterceptorAspect
 * @ProjectName gateway
 * @date 2019/11/23下午3:48
 * @Description: 拦截器切面
 */
@Slf4j
@Component
@Aspect
public class InterceptorAspect {

    @Autowired
    IGatewayInterfaceService gatewayInterfaceService;

    @Pointcut("execution(* com.ocft.gateway.handler..AbstractControllerHandler.handle(..))")
    public void interceptPointCut() {
    }

    @Before("interceptPointCut() && args(gatewayContext)")
    public void before(JoinPoint joinPoint, GatewayContext gatewayContext) {
        System.err.println("before");
        this.doExecuteIntercept(gatewayContext.getGatewayInterface().getPreInterceptors(), gatewayContext);
    }

    @After(value = "interceptPointCut() && args(gatewayContext)")
    public void after(JoinPoint joinPoint, GatewayContext gatewayContext) {
        System.err.println("after");
        this.doExecuteIntercept(gatewayContext.getGatewayInterface().getPostInterceptors(), gatewayContext);
    }

    @AfterThrowing(value = "interceptPointCut()")
    public void throwsExcetion(JoinPoint joinPoint) {
        System.err.println("throw some Exception!");
    }

    private void doExecuteIntercept(String interceptors, GatewayContext gatewayContext) {
        if (StringUtils.hasText(interceptors)) {
            String[] classNames = interceptors.split(",");
            for (String className : classNames) {
                GatewayInterceptor interceptor = SpringContextHolder.getBean(className);
                interceptor.doInterceptor(gatewayContext);
            }
        }
    }

}
