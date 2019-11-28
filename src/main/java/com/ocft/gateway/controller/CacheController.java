package com.ocft.gateway.controller;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private IGatewayCacheService gatewayCacheService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/global/refresh")
    public Map<String, Object> globalRefresh(){
        List<GatewayCache> globalCaches =gatewayCacheService.list();
        for (GatewayCache  globalCache :globalCaches) {
            refreshCache(globalCache);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("刷新全局缓存",ResponseEnum.SUCCESS);
        return result;
    }

    @GetMapping("/api/refresh")
    public Map<String, Object> apiRefresh(@RequestParam("api") String url){
        GatewayCache globalCache =gatewayCacheService.getGatewayCache(url);
        refreshCache(globalCache);
        Map<String, Object> result = new HashMap<>();
        result.put("刷新缓存",ResponseEnum.SUCCESS);
        return result;
    }

    private void refreshCache(GatewayCache globalCache){
        try {
            //获取url对应的所有field
            Set<Object> fields = redisUtil.getFields(globalCache.getUrl());
            Iterator<Object> iterator = fields.iterator();
            Map<Object,Object> redisHash = new HashMap<>();
            while (iterator.hasNext()){
                String next = iterator.next().toString();
                System.out.println(next);
                //TODO   转换为 request body 并且向接口发请求

                String result = "apiResultTest";
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
