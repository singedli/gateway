package com.ocft.gateway.spring;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author lijiaxing
 * @Title: SpringContextHolder
 * @ProjectName gateway
 * @date 2019/11/22上午9:15
 * @Description: spring 容器持有者
 */
@Service
public class SpringContextHolder implements ApplicationContextAware, InitializingBean, DisposableBean,BeanFactoryAware {
    private static ApplicationContext applicationContext = null;

    public static DefaultListableBeanFactory listableBeanFactory;

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

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory)beanFactory;
        this.listableBeanFactory = listableBeanFactory;
    }

    public static void setBean(String beanName,Object object){
        //注意这里放入的bean在容器中是单例的
        listableBeanFactory.registerSingleton(beanName,object);
    }

    //根据beanName销毁(删除)单例的bean
    public static void removeSingletonBean(String beanName){
        listableBeanFactory.destroySingleton(beanName);
    }

    //手动把对象放入容器中,但是可以设置作用域
    public static void setBean(String beanName,Class<?> clazz){
        BeanDefinition beanDefinition =  new RootBeanDefinition();
        beanDefinition.setBeanClassName(clazz.getName());
        //设置作用域
        beanDefinition.setScope("prototype");
        listableBeanFactory.registerBeanDefinition(beanName,beanDefinition);
    }

    //根据beanName删除使用BeanDefinition创建的bean , Spring默认就是使用BeanDefinition创建的bean对象
    public static void removeBean(String beanName){
        listableBeanFactory.removeBeanDefinition(beanName);
    }
}
