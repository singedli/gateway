package com.ocft.gateway.controller;

import com.ocft.gateway.cache.HandlerTypeCache;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.converter.JsonCacheDataConverter;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.HandlerType;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.handler.AbstractControllerHandler;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/28 10:32
 * @Description:
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private IGatewayInterfaceService gatewayInterfaceService;

    @Autowired
    private IGatewayCacheService gatewayCacheService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HandlerTypeCache handlerTypeCache;

    @GetMapping("/global/refresh")
    public Map<String, Object> globalRefresh(HttpServletRequest request,HttpServletResponse response){
        List<GatewayCache> globalCaches =gatewayCacheService.list();
        for (GatewayCache  globalCache :globalCaches) {
            refreshCache(globalCache,request,response);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("刷新全局缓存",ResponseEnum.SUCCESS);
        return result;
    }

    @GetMapping("/api/refresh")
    public Map<String, Object> apiRefresh(@RequestParam("api") String url, HttpServletRequest request,HttpServletResponse response){
        GatewayCache globalCache =gatewayCacheService.getGatewayCache(url);
        refreshCache(globalCache,request,response);
        Map<String, Object> result = new HashMap<>();
        result.put("刷新缓存",ResponseEnum.SUCCESS);
        return result;
    }

    private void refreshCache(GatewayCache globalCache,HttpServletRequest request,HttpServletResponse response){
        try {
            GatewayInterface gateWayInterface = gatewayInterfaceService.getGateWayInterface(globalCache.getUrl());
            AbstractControllerHandler handler = (AbstractControllerHandler) handlerTypeCache.getHandler(HandlerType.valueOf(gateWayInterface.getType()));

            //获取url对应的所有field
            Set<Object> fields = redisUtil.getFields(globalCache.getUrl());
            Iterator<Object> iterator = fields.iterator();
            Map<Object,Object> redisHash = new HashMap<>();
            while (iterator.hasNext()){
                String next = iterator.next().toString();
                System.out.println(next);

                GatewayContext gatewayContext = new GatewayContext();
                gatewayContext.setGatewayCache(globalCache);
                gatewayContext.setGatewayInterface(gateWayInterface);
                gatewayContext.setRequest(request);
                gatewayContext.setResponse(response);

                String requestBody = JsonCacheDataConverter.getRequestBody(next, globalCache);
                gatewayContext.setRequestBody(requestBody);

                String result = handler.sendToBacon(gatewayContext);
                        
                //String result = "apiResultTest";
                redisHash.put(next,result);

                //如果缓存存在则删除
                if(redisUtil.existsHash(globalCache.getUrl(),next)){
                    redisUtil.hdel(globalCache.getUrl(),next);
                }
            }

            //更新缓存
            redisUtil.hset(globalCache.getUrl(),redisHash,globalCache.getExpireTime()/60);
        }catch (Exception e){
            e.printStackTrace();
            throw new GatewayException(ResponseEnum.GATEWAY_CACHE_REFRESH_FAIL);
        }
    }
}
