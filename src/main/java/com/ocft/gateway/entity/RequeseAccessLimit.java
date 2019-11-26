package com.ocft.gateway.entity;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * @author: Bobby
 * @create: 2019-11-25 17:57
 * @description: 防恶意请求限制
 **/


@Retention(RUNTIME)
@Target(METHOD)
public @interface RequeseAccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin()default true;
}
