package com.ocft.gateway.CacheTest;

import com.ocft.gateway.cache.GatewayLocalCache;

/**
 * @author lijiaxing
 * @Title: TestCreateCache
 * @ProjectName gateway
 * @date 2019/12/5下午2:24
 * @Description: TODO
 */
public class TestCreateCache {
    public static void main(String[] args) throws InterruptedException {
        GatewayLocalCache cache = new GatewayLocalCache();
        cache.put("a","1");
        cache.put("b","2");
        cache.put("c","3");
        cache.put("d","4");
        cache.put("e","5");

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

        System.out.println(cache.toString());
        System.out.println(cache.sizeof());

        System.out.println("--------------------");
        cache.put("f","6");
        System.out.println(cache.toString());

        System.out.println(cache.sizeof());


    }
}
