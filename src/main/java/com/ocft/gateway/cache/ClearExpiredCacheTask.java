package com.ocft.gateway.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/16 13:44
 * @Description:
 */
@Slf4j
@Data
public class ClearExpiredCacheTask<K, V> implements Runnable {

    private GatewayCache cache;

    ClearExpiredCacheTask(){

    }

    ClearExpiredCacheTask(GatewayCache cache){
        this.cache = cache;
    }

    @Override
    public void run() {
        Iterator<Map.Entry<K, CacheDataWrapper<K, V>>> iterator = cache.getIterator();
        while (iterator.hasNext()) {
            Map.Entry<K, CacheDataWrapper<K, V>> next = iterator.next();
            CacheDataWrapper<K, V> dataWrapper = next.getValue();
            boolean expired = dataWrapper.isExpired();
            if (expired) {
                cache.remove(next.getKey());
            }
        }
    }
}
