package com.ocft.gateway.CacheTest;

import com.ocft.gateway.cache.GatewayLocalCache;

import java.util.concurrent.TimeUnit;


/**
 * @author lijiaxing
 * @Title: TestCacheTTL
 * @ProjectName gateway
 * @date 2019/12/6上午10:53
 * @Description: TODO
 */
public class TestCacheTTL {
    public static void main(String[] args) throws InterruptedException {
        GatewayLocalCache cache = new GatewayLocalCache(1300L);
        cache.put("a", "1", 1000 *5);
        cache.put("b", "2");
        cache.put("c", "3");
        cache.put("d", "4");
        cache.put("e", "5");

        cache.get("a");
        cache.get("a");
        cache.get("b");
        cache.get("c");
        cache.get("c");
        cache.get("c");
        cache.get("c");
        cache.get("d");
        cache.get("e");
        cache.get("e");
        cache.get("e");
        System.out.println(cache);
        TimeUnit.SECONDS.sleep(5);
        System.out.println("-----------");
        System.out.println(cache.get("a"));
        System.out.println(cache);
    }
}
