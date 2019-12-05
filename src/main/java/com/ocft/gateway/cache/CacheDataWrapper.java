package com.ocft.gateway.cache;

/**
 * @author lijiaxing
 * @Title: CacheDataWrapper
 * @ProjectName gateway
 * @date 2019/12/4下午5:12
 * @Description: 缓存对象
 */
public class CacheDataWrapper<K, V> {
    /**
     * 缓存的值
     */
    private V value;
    /**
     * 缓存对象存活时间
     *   默认为 0
     *   已过期则为-1
     */
    private long ttl;
    /**
     * 上次访问的时间(时间戳)
     */
    private long lastAccessTime;
    /**
     * 访问次数
     */
    private int accessCount;


    public CacheDataWrapper(V value, long ttl, long lastAccessTime, Integer accessCount) {
        this.value = value;
        this.ttl = ttl;
        this.lastAccessTime = lastAccessTime;
        this.accessCount = accessCount;
    }

    /**
     * 判断当前缓存对象是否过期
     *
     * @return
     */
    public boolean isExpired() {
        Boolean expired = Boolean.FALSE;
        if (ttl > 0) {
            expired = System.currentTimeMillis() - lastAccessTime > ttl;
        }
        return expired;
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
        if(updateExpiringTime){
            this.lastAccessTime = System.currentTimeMillis();
        }
        accessCount++;
        return get();
    }

    public int getAccessCount() {
        return accessCount;
    }

    @Override
    public String toString() {
        return "CacheDataWrapper{" +
                "value=" + value +
                ", ttl=" + ttl +
                ", lastAccessTime=" + lastAccessTime +
                ", accessCount=" + accessCount +
                '}';
    }
}
