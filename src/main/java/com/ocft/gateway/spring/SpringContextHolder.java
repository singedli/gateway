package com.ocft.gateway.spring;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author lijiaxing
 * @Title: SpringContextHolder
 * @ProjectName gateway
 * @date 2019/11/22上午9:15
 * @Description: spring 容器
 */
@Service
public class SpringContextHolder implements ApplicationContextAware, InitializingBean, DisposableBean {
    private static ApplicationContext applicationContext = null;

    /**
     * 获取Spring加载bean对象
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T)getApplicationContext().getBean(name);
    }

    /**
     * 获取Spring加载bean对象
     * @param name
     * @param requiredType
     * @return
     */
    public static <T> T getBean(String name,Class<T> requiredType) {
        return getApplicationContext().getBean(name,requiredType);
    }

    /**
     * 获取Spring加载bean对象
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }

    /**
     * 获取ApplicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 继承ApplicationContextAware，启动时会执行此方法注入ApplicationContext
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Validate.isTrue(applicationContext != null, "applicaitonContext属性未注入!");
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }
}
