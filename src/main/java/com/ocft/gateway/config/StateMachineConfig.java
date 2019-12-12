package com.ocft.gateway.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IGatewayInterfaceService;
import io.seata.saga.engine.impl.DefaultStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
@Slf4j
@Configuration
public class StateMachineConfig {

//    @Value("classpath:saga/statelang/*.json")
//    private Resource[] stateLang;

    @Autowired
    private IGatewayInterfaceService gatewayInterfaceService;

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

        config.setResources(getStateLang());
        config.setEnableAsync(Boolean.TRUE);
        config.setThreadPoolExecutor(threadExecutor());
        return config;
    }

    private Resource[] getStateLang(){
        List<Resource> resources = new ArrayList<>();
        List<GatewayInterface> gatewayInterfaces = gatewayInterfaceService.getBaseMapper().selectList(new QueryWrapper<GatewayInterface>().eq("type","COMPLICATE").eq("status","1").eq("is_deleted","0"));
        for (GatewayInterface gatewayInterface : gatewayInterfaces) {
            String invokeConfig = gatewayInterface.getInvokeConfig();
            if(StringUtils.hasText(invokeConfig)){
                ByteArrayInputStream inputStream = new ByteArrayInputStream(invokeConfig.getBytes());
                Resource resource = new InputStreamResource(inputStream);
                resources.add(resource);
            }
        }
        log.info("状态机脚本初始化完成,共加载脚本 {} 个",resources.size());
        return resources.toArray(new Resource[resources.size()]);
    }
}
