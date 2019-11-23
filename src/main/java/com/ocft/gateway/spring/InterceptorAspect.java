package com.ocft.gateway.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

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

    @Pointcut("execution(* com.ocft.gateway.controller..CompositeBussinessController.compositeHandler(..))")
    public void interceptPointCut() {}

    @Before("interceptPointCut()")
    public void preIntercept(JoinPoint joinPoint){
        log.info("preIntercept executed!");
    }

    @After(value = "interceptPointCut()")
    public void after(JoinPoint joinPoint){
        log.info("postIntercept executed!");
    }

    @AfterThrowing(value = "interceptPointCut()")
    public void throwss(JoinPoint joinPoint){
        log.info("throw some Exception!");
    }

}
