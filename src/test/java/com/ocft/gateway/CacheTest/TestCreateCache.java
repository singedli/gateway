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
        GatewayLocalCache cache = new GatewayLocalCache(1300L);
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


//        SoftHashMap softHashMap = new SoftHashMap();
//        softHashMap.put("a","1");
//        softHashMap.put("b","2");
//        softHashMap.put("c","3");
//        softHashMap.put("d","4");
//        softHashMap.put("e","5");
//        Iterator<Map.Entry<String,String>> iterator =
//                softHashMap.entrySet().iterator();
//        while(iterator.hasNext()){
//            iterator.remove();
//        }
//
//        System.out.println(softHashMap);
    }
}
