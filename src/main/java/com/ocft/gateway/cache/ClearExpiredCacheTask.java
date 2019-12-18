package com.ocft.gateway.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
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

    private AbstractGatewayCache cache;

    ClearExpiredCacheTask(){

    }

    ClearExpiredCacheTask(AbstractGatewayCache cache){
        this.cache = cache;
    }

    @Override
    public void run() {
        log.info("本地缓存后台线程开启：{}",Thread.currentThread().getName());
        while (true){
            BigDecimal sizeof = new BigDecimal(cache.sizeof());
            BigDecimal maxCacheSize = new BigDecimal(cache.maxCacheSize);
            if ( sizeof.divide(maxCacheSize,2,BigDecimal.ROUND_HALF_UP).doubleValue()> 0.75) {
                Iterator<Map.Entry<K, CacheDataWrapper<K, V>>> iterator = cache.getIterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, CacheDataWrapper<K, V>> next = iterator.next();
                    CacheDataWrapper<K, V> dataWrapper = next.getValue();
                    boolean expired = dataWrapper.isExpired();
                    if (expired) {
                        cache.remove(next.getKey());
                        log.info("本地缓存被清理：{}", next.getKey() + ":" + next.getValue());
                    }
                }
            }
        }
    }
}
