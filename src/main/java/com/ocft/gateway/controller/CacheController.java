package com.ocft.gateway.controller;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
            List<GatewayCache> globalCaches =gatewayCacheService.list();
            for (GatewayCache  globalCache :globalCaches) {
                Map map = redisUtil.get(globalCache.getUrl(), Map.class);
                redisUtil.hset(globalCache.getUrl(),map,globalCache.getExpireTime());
            }
        }catch (Exception e){
            throw new GatewayException(ResponseEnum.GATEWAY_CACHE_REFRESH_FAIL);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("刷新全局缓存",ResponseEnum.SUCCESS);
        return result;
    }

    @GetMapping("/api/refresh")
    public Map<String, Object> apiRefresh(@RequestParam("api") String url){
        try {
            GatewayCache globalCache =gatewayCacheService.getGatewayCache(url);
            Map map = redisUtil.get(globalCache.getUrl(), Map.class);
            redisUtil.hset(globalCache.getUrl(),map,globalCache.getExpireTime());
        }catch (Exception e){
            throw new GatewayException(ResponseEnum.GATEWAY_CACHE_REFRESH_FAIL);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("刷新缓存",ResponseEnum.SUCCESS);
        return result;
    }
}
