package com.ocft.gateway.cache;

import com.ocft.gateway.spring.SpringContextHolder;
import com.ocft.gateway.enums.HandlerType;
import com.ocft.gateway.handler.ComplicatedControllerHandler;
import com.ocft.gateway.handler.ConcurrentControllerHandler;
import com.ocft.gateway.handler.IControllerHandler;
import com.ocft.gateway.handler.PassThroughControllerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: HandlerTypeCache
 * @ProjectName gateway
 * @date 2019/11/22下午2:18
 * @Description: 处理器缓存，项目启动时初始化
 */
@Slf4j
@Component
public class HandlerTypeCache implements CommandLineRunner {

    private static Map<HandlerType, IControllerHandler> cache;

    private HandlerTypeCache(){}

    @Override
    public void run(String... args) throws Exception {
        cache = new HashMap<>(3);
        cache.put(HandlerType.PASS,SpringContextHolder.getBean(PassThroughControllerHandler.class));
        cache.put(HandlerType.CONCURRENT,SpringContextHolder.getBean(ConcurrentControllerHandler.class));
        cache.put(HandlerType.COMPLICATE,SpringContextHolder.getBean(ComplicatedControllerHandler.class));
        log.info("loading controller handler finished！type is [{},{},{}]",HandlerType.PASS.name(),HandlerType.CONCURRENT.name(),HandlerType.COMPLICATE.name());
    }


    public IControllerHandler getHandler(HandlerType type){
        return cache.get(type);
    }
}
