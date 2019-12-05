package com.ocft.gateway.cache;

/**
 * @author lijiaxing
 * @Title: CacheObject
 * @ProjectName gateway
 * @date 2019/12/4下午5:12
 * @Description: 缓存对象
 */
public class CacheObject<K, V> {

    public CacheObject(V value, long ttl, long expiringTime) {
        this.value = value;
        this.ttl = ttl;
        this.expiringTime = expiringTime;
    }

    /**
     * 缓存的值
     */
    private V value;
    /**
     * 缓存对象存活时间
     */
    private long ttl;
    /**
     * 过期时间
     */
    private long expiringTime;

    /**
     * 判断当前缓存对象是否过期
     *
     * @return
     */
    public boolean isExpired() {
        if (ttl > 0) {
            long currentTime = System.currentTimeMillis() + ttl;
            return expiringTime > 0 && (expiringTime - currentTime < 0);
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * @return
     */
    public V get() {
        return value;
    }

    /**
     * @param updateExpiringTime 是否刷新过期时间
     * @return
     */
    public V get(Boolean updateExpiringTime) {
        //刷新过期时间
        if (updateExpiringTime)
            expiringTime = System.currentTimeMillis() + ttl;
        return value;
    }

}
