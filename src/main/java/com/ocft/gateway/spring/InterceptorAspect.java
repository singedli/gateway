package com.ocft.gateway.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;

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

    @Pointcut("execution(* com.ocft.gateway.handler..AbstractControllerHandler.handle(..))")
    public void interceptPointCut() {
    }

    @Before("interceptPointCut()")
    public void preIntercept(JoinPoint joinPoint) {
        System.err.println("preIntercept executed!");
    }

    @After(value = "interceptPointCut()")
    public void after(JoinPoint joinPoint) {
        System.err.println("postIntercept executed!");
    }

    @AfterThrowing(value = "interceptPointCut()")
    public void throwss(JoinPoint joinPoint) {
        System.err.println("throw some Exception!");
    }

}
