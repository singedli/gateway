package com.ocft.gateway.config;

import io.seata.saga.engine.impl.DefaultStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lijiaxing
 * @Title: StateMachineConfig
 * @ProjectName gateway
 * @date 2019/12/9下午3:05
 * @Description: TODO
 */
@Configuration
public class StateMachineConfig {

    @Value("classpath:saga/statelang/*.json")
    private Resource[] stateLang;

    @Bean
    public ProcessCtrlStateMachineEngine stateMachineEngine() {
        ProcessCtrlStateMachineEngine engine = new ProcessCtrlStateMachineEngine();
        engine.setStateMachineConfig(defaultStateMachineConfig());
        return engine;
    }

    @Bean
    public ThreadPoolExecutor threadExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,20,TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        return threadPoolExecutor;
    }

    @Bean
    public DefaultStateMachineConfig defaultStateMachineConfig(){
        DefaultStateMachineConfig config = new DefaultStateMachineConfig();
        config.setResources(stateLang);
        config.setEnableAsync(Boolean.TRUE);
        config.setThreadPoolExecutor(threadExecutor());
        return config;
    }
}
