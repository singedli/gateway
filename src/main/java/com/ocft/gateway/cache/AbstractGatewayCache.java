package com.ocft.gateway.cache;

import org.apache.lucene.util.RamUsageEstimator;
import org.aspectj.util.SoftHashMap;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Iterator;
import java.util.Map;
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
     * 缓存，使用软引用，会在内存不够时将缓存回收
     */
    //public Map<K, CacheDataWrapper<K, V>> cache = new SoftHashMap<>();
      public Map<K, CacheDataWrapper<K, V>> cache = new ConcurrentReferenceHashMap<>(16, ConcurrentReferenceHashMap.ReferenceType.SOFT);

//    /**
//     * 缓存锁
//     */
//    protected static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
//    /**
//     * 读锁
//     */
//    protected static final ReentrantReadWriteLock.ReadLock readLock = cacheLock.readLock();
//    /**
//     * 写锁
//     */
//    protected static final ReentrantReadWriteLock.WriteLock writeLock = cacheLock.writeLock();
    /**
     * 缓存在堆中占用的最大内存，默认为4000MB
     */
    protected long maxCacheSize = RamUsageEstimator.ONE_MB * 4000;
    /**
     * 缓存当前所占用的内存大小
     */
    protected long currentCacheSize = RamUsageEstimator.sizeOf(this.cache);

    @Override
    public void put(K key, V value) {
//        writeLock.lock();
//        try {
//            doPut(key, value, -1);
//        } finally {
//            writeLock.unlock();
//        }
        doPut(key, value, -1);
    }

    @Override
    public void put(K key, V value, long ttl) {
//        writeLock.lock();
//        try {
//            doPut(key, value, ttl);
//        } finally {
//            writeLock.unlock();
//        }
        doPut(key, value, ttl);
    }

    private void doPut(K key, V value, long ttl) {
        long current = System.currentTimeMillis();
        CacheDataWrapper dataWrapper = new CacheDataWrapper(value, ttl, current, 0);
        increaseCurrentCacheSize(dataWrapper);
        this.cache.put(key, dataWrapper);
    }

    /**
     *
     * @param obj
     * @return
     */
    private long increaseCurrentCacheSize(Object obj) {
        long size = RamUsageEstimator.sizeOf(obj);
        while (this.sizeof() + size >= this.maxCacheSize) {
            //使用具体的策略清除缓存数据
            reduce(size);
        }
        /**
         *  75%
         */
        if ((this.sizeof() + size) / this.maxCacheSize > 0.75) {
            //异步执行扫描任务，将已过期的数据从缓存中剔除

        }
        return currentCacheSize += size;
    }


    @Override
    public V get(K key) {
//        readLock.lock();
//        CacheDataWrapper<K, V> dataWrapper = null;
//        try {
//            dataWrapper = this.cache.get(key);
//            if (dataWrapper == null) {
//                return null;
//            }
//        } finally {
//            readLock.unlock();
//        }
        CacheDataWrapper<K, V> dataWrapper = this.cache.get(key);
        if (dataWrapper == null) {
            return null;
        }

        dataWrapper = this.cache.get(key);
        if (dataWrapper == null) {
            return null;
        }
        if (dataWrapper.isExpired()) {
            this.remove(key);
        }
        return dataWrapper.get(Boolean.TRUE);
    }

    @Override
    public void remove(K key) {
//        writeLock.lock();
//        try {
//            this.cache.remove(key);
//        } finally {
//            writeLock.unlock();
//        }
        this.cache.remove(key);
    }

    @Override
    public int size() {
//        readLock.lock();
//        try {
//            return this.cache.size();
//        } finally {
//            readLock.unlock();
//        }
        return this.cache.size();
    }

    @Override
    public long sizeof() {
//        readLock.lock();
//        try {
//            return RamUsageEstimator.sizeOf(this.cache);
//        } finally {
//            readLock.unlock();
//        }
        return RamUsageEstimator.sizeOf(this.cache);
    }

    @Override
    public boolean isEmpty() {
//        readLock.lock();
//        try {
//            return this.cache.isEmpty();
//        } finally {
//            readLock.unlock();
//        }
        return this.cache.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
//        readLock.lock();
//        try {
//            CacheDataWrapper<K, V> dataWrapper = this.cache.get(key);
//            if (dataWrapper == null) {
//                return Boolean.FALSE;
//            }
//
//            if (dataWrapper.isExpired()) {
//                remove(key);
//            }
//        } finally {
//            readLock.unlock();
//        }
        CacheDataWrapper<K, V> dataWrapper = this.cache.get(key);
        if (dataWrapper == null) {
            return Boolean.FALSE;
        }

        if (dataWrapper.isExpired()) {
            remove(key);
        }
        return Boolean.TRUE;
    }

    @Override
    public int clear() {
//        writeLock.lock();
//        int total = cache.size();
//        try {
//            cache.clear();
//        } finally {
//            writeLock.unlock();
//        }
        cache.clear();
        return cache.size();
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
