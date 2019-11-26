package com.ocft.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.handler.AbstractControllerHandler;
import com.ocft.gateway.handler.IControllerHandler;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.spring.SpringContextHolder;
import com.sun.deploy.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
    public void interceptPointCut() {}


    @Around(value = "interceptPointCut() && args(gatewayContext)")
    public void around(ProceedingJoinPoint joinPoint, GatewayContext gatewayContext) throws Throwable {
        this.doExecuteIntercept(gatewayContext.getGatewayInterface().getPreInterceptors(), gatewayContext);
        joinPoint.proceed(new Object[] {gatewayContext});
        this.doExecuteIntercept(gatewayContext.getGatewayInterface().getPostInterceptors(), gatewayContext);
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
