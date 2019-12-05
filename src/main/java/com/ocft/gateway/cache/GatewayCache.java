package com.ocft.gateway.cache;

import java.util.Iterator;

/**
 * @author lijiaxing
 * @Title: GatewayCache
 * @ProjectName gateway
 * @date 2019/12/4下午4:11
 * @Description: 缓存接口
 */
public interface GatewayCache<K, V> {
    /**
     * 将数据加入到缓存,数据存活时长不做限制
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     *  将数据加入到缓存,数据存活时长为ttl,单位为秒
     * @param key
     * @param value
     * @param ttl
     */
    void put(K key, V value,Integer ttl);

    /**
     *  从缓存中获取数据
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 从缓存中删除指定的数据
     * @param key
     */
    void remove(K key);

    /**
     * 获取当前缓存中数据的总数
     * @return
     */
    int size();

    /**
     *
     * @return
     */
    long sizeof();

    /**
     * 判断当前缓存是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 判断缓存是否存在
     * @param key
     * @return
     */
    boolean containsKey(K key);

    /**
     * 清空所有缓存
     * @return 被删除数据的个数
     */
    int clear();

    /**
     * 获取缓存Map的迭代器
     * @return
     */
    Iterator getIterator();

    /**
     * 根据不同的策略清除缓存中的数据
     */
    int reduce(long size);

}
