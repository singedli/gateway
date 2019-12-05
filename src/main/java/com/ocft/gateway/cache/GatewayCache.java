package com.ocft.gateway.cache;

/**
 * @author lijiaxing
 * @Title: GatewayCache
 * @ProjectName gateway
 * @date 2019/12/4下午4:11
 * @Description: 缓存接口
 */
public interface GatewayCache<K, V> {
    /**
     * 将数据加入到缓存
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     *  从缓存中获取数据
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 从缓存中删除数据
     * @param key
     */
    void remove(K key);

    /**
     * 获取当前缓存中数据的总数
     * @return
     */
    int size();

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
     * @return
     */
    int clear();
}
