package com.ocft.gateway.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author lijiaxing
 * @Title: GatewayLocalCache
 * @ProjectName gateway
 * @date 2019/12/5下午3:20
 * @Description: 缓存淘汰策略使用LFU(Least Frequently Used) ,即最近最少使用
 * <p>
 * 如果一个数据在最近一段时间很少被访问到，那么可以认为在将来它被访问的可能性也很小。
 * 因此，当空间满时，最小频率访问的数据最先被淘汰。
 */
public class GatewayLocalCache<K, V> extends AbstractGatewayCache<K, V> {

    /**
     * @param maxCacheSize 缓存最大所占用的内存空间
     *                     <p>
     *                     n * RamUsageEstimator.ONE_KB; 表示nK大小
     *                     n * RamUsageEstimator.ONE_MB; 表示nM大小
     *                     n * RamUsageEstimator.ONE_GB; 表示nG大小
     */
    public GatewayLocalCache(long maxCacheSize) {
        Thread thread = new Thread(new ClearExpiredCacheTask(this));
        thread.setDaemon(true);
        thread.start();
        super.maxCacheSize = maxCacheSize;
    }

    /**
     * 使用缓存最大所占用的内存空间的默认值，即500MB
     */
    public GatewayLocalCache() {

    }

    private int minAccessCount = 0;

    @Override
    public int reduce(long size) {
        int count = 0;
        Iterator<Map.Entry<K, CacheDataWrapper<K, V>>> iterator = super.getIterator();
        while (iterator.hasNext()) {
            Map.Entry<K, CacheDataWrapper<K, V>> next = iterator.next();
            CacheDataWrapper<K, V> dataWrapper = next.getValue();
            boolean expired = dataWrapper.isExpired();
            if (expired) {
                super.remove(next.getKey());
                count++;
            }

            if (minAccessCount == 0 || dataWrapper.getAccessCount() < minAccessCount)
                minAccessCount = dataWrapper.getAccessCount();
        }

        /**
         * 有过期数据被处理的情况不执行下面代码
         *
         * 遍历缓存中的数据，使缓存中每一个CacheDataWrapper的accessCount值都减minAccessCount
         * 目的是保证公平
         */
        if (super.sizeof() + size > super.maxCacheSize) {
            Set<Map.Entry<K, CacheDataWrapper<K, V>>> entries = super.cache.entrySet();
            for (Map.Entry<K, CacheDataWrapper<K, V>> entry : entries) {
                CacheDataWrapper<K, V> dataWrapper = entry.getValue();
                if (dataWrapper.getAccessCount() - minAccessCount <= 0) {
//                    writeLock.lock();
//                    try {
//                        this.cache.remove(entry.getKey());
//                    } finally {
//                        writeLock.unlock();
//                    }
                    this.cache.remove(entry.getKey());
                    count++;
                }
            }
        }
        return count;
    }
}
