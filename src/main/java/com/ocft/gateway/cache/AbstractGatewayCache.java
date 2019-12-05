package com.ocft.gateway.cache;


import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lijiaxing
 * @Title: AbstractGatewayCache
 * @ProjectName gateway
 * @date 2019/12/4下午4:24
 * @Description: 抽象缓存类
 */
public class AbstractGatewayCache<K,V> implements GatewayCache<K,V> {

    /**
     * 缓存，使用弱引用，会在下一次GC时将缓存回收
     */
    protected Map<K,V> cache = new WeakHashMap();
    /**
     * 缓存锁
     */
    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private final ReentrantReadWriteLock.ReadLock readLock = cacheLock.readLock();
    /**
     * 写锁
     */
    private final ReentrantReadWriteLock.WriteLock writeLock = cacheLock.writeLock();


    @Override
    public void put(K key, V value) {
        writeLock.lock();
        try{
            cache.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public V get(K key) {
        readLock.lock();
        V v = null;
        try{
            v =  cache.get(key);
        }finally {
            readLock.unlock();
        }
        return v;
    }

    @Override
    public void remove(Object key) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public int clear() {
        return 0;
    }
}
