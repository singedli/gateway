package com.ocft.gateway.cache;


import org.apache.lucene.util.RamUsageEstimator;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lijiaxing
 * @Title: AbstractGatewayCache
 * @ProjectName gateway
 * @date 2019/12/4下午4:24
 * @Description: 抽象缓存类
 */
public abstract class AbstractGatewayCache<K, V> implements GatewayCache<K, V> {

    /**
     * 缓存，使用弱引用，会在下一次GC时将缓存回收
     */
    protected Map<K, CacheDataWrapper<K, V>> cache = new WeakHashMap();
    /**
     * 缓存锁
     */
    protected static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    protected static final ReentrantReadWriteLock.ReadLock readLock = cacheLock.readLock();
    /**
     * 写锁
     */
    protected static final ReentrantReadWriteLock.WriteLock writeLock = cacheLock.writeLock();
    /**
     * 缓存在堆中占用的最大内存
     */
//    protected static final long maxCacheSize = RamUsageEstimator.ONE_MB * 50;
    protected static final long maxCacheSize = 1000L;
    /**
     * 缓存当前所占用的内存大小
     */
    protected long currentCacheSize = RamUsageEstimator.sizeOf(this.cache);

    @Override
    public void put(K key, V value) {
        writeLock.lock();
        try {
            doPut(key, value, -1);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void put(K key, V value, Integer ttl) {
        writeLock.lock();
        try {
            doPut(key, value, ttl);
        } finally {
            writeLock.unlock();
        }
    }

    private void doPut(K key, V value, Integer ttl) {
        long current = System.currentTimeMillis();
        CacheDataWrapper dataWrapper = new CacheDataWrapper(value, ttl, current, 0);
        increaseCurrentCacheSize(dataWrapper);
        this.cache.put(key, dataWrapper);
    }

    private long increaseCurrentCacheSize(Object obj) {
        long size = RamUsageEstimator.sizeOf(obj);
        while (this.sizeof() + size >= this.maxCacheSize) {
            //使用具体的策略清除缓存数据
            reduce(size);
        }
        /**
         *  75%
         */
//        if ((this.sizeof() + size) / this.maxCacheSize > 0.75) {
//            //异步执行扫描任务，将已过期的数据从缓存中剔除
//        }
//        while (this.currentCacheSize + size >= this.maxCacheSize) {
//            //使用具体的策略清除缓存数据
//            reduce(size);
//        }
//        /**
//         *  75%
//         */
//        if ((this.currentCacheSize + size) / this.maxCacheSize > 0.75) {
//            //异步执行扫描任务，将已过期的数据从缓存中剔除
//        }
        return currentCacheSize += size;
    }


    @Override
    public V get(K key) {
        readLock.lock();
        try {
            CacheDataWrapper<K, V> dataWrapper = this.cache.get(key);
            if (dataWrapper == null) {
                return null;
            }

            if (dataWrapper.isExpired()) {
                this.remove(key);
            }
            return dataWrapper.get(Boolean.TRUE);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void remove(K key) {
        writeLock.lock();
        try {
            this.cache.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int size() {
        readLock.lock();
        try {
            return this.cache.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public long sizeof() {
        readLock.lock();
        try {
            return RamUsageEstimator.sizeOf(this.cache);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        readLock.lock();
        try {
            return this.cache.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsKey(K key) {
        readLock.lock();
        try {
            CacheDataWrapper<K, V> dataWrapper = this.cache.get(key);
            if (dataWrapper == null) {
                return Boolean.FALSE;
            }

            if (dataWrapper.isExpired()) {
                remove(key);
            }
        } finally {
            readLock.unlock();
        }
        return Boolean.TRUE;
    }

    @Override
    public int clear() {
        writeLock.lock();
        int total = cache.size();
        try {
            cache.clear();
        } finally {
            writeLock.unlock();
        }
        return total;
    }

    @Override
    public Iterator<Map.Entry<K, CacheDataWrapper<K, V>>> getIterator() {
        return this.cache.entrySet().iterator();
    }

    @Override
    public abstract int reduce(long size);

    @Override
    public String toString() {
        String s = "GatewayCache{\n";
        Iterator<Map.Entry<K, CacheDataWrapper<K, V>>> iterator = getIterator();
        while (iterator.hasNext()) {
            Map.Entry<K, CacheDataWrapper<K, V>> next = iterator.next();
            K key = next.getKey();
            CacheDataWrapper<K, V> value = next.getValue();
            s = s + key + "->" + value + "\n";
        }
        return s + "}";
    }
}
