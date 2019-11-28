package com.ocft.gateway.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/22 15:10
 * @Description:
 */
@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Boolean set(final String key,final String value,final int expiresTime) {
        redisTemplate.opsForValue().set(key, value);
        boolean flag = Boolean.TRUE;
        if(expiresTime > 0){
            flag = expire(key,expiresTime);
        }
        return flag;
    }

    public Boolean set(final String key,final String value){
        return set(key,value,0);
    }

    public <T> T get(final String key, final Class<T> clazz) {
        return JSON.parseObject(redisTemplate.opsForValue().get(key),clazz);
    }

    public String get(final String key){
        return get(key,String.class);
    }

    public Boolean delete(final String key) {
        return redisTemplate.delete(key);
    }

    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean expire(final String key, final int expiresTime) {
        return redisTemplate.expire(key,expiresTime,TimeUnit.SECONDS);
    }

    public Long ttl(final String key) {
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    public Long incr(final String key) {
        return redisTemplate.opsForValue().increment(key,1);
    }

    public Boolean hset(final String key, final String field, final String value,final int expiresTime) {
        redisTemplate.opsForHash().put(key, field, value);
        boolean flag = Boolean.TRUE;
        if(expiresTime > 0){
            flag = expire(key,expiresTime);
        }
        return flag;
    }

    public Boolean hset(final String key, final String field, final String value) {
        return hset(key,field,value,0);
    }

    public Boolean hset(final String key, final Map<Object,Object> hash,final int expiresTime) {
        redisTemplate.opsForHash().putAll(key, hash);
        boolean flag = Boolean.TRUE;
        if(expiresTime > 0){
            flag = expire(key,expiresTime);
        }
        return flag;
    }

    public Boolean hset(final String key, final Map<Object,Object> hash){
        return hset(key,hash,0);
    }

    public Object hget(final String key, final String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Long hdel(final String key, final String... field) {
        return redisTemplate.opsForHash().delete(key, field);
    }

    public Set<Object> getFields(final String key){
        return redisTemplate.opsForHash().keys(key);
    }

    public Map<Object, Object> getHashMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public List<Object> getList(final String key){
        return redisTemplate.opsForHash().values(key);
    }

    public Boolean existsHash(final String key, final String field){
        return redisTemplate.opsForHash().hasKey(key,field);
    }

    public Boolean setNX(final String key,final String value) {
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }

    public Object getset(final String key,final String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public Boolean lock(final String lockKey ,final int expireTime, final long timeout){
        logger.info("try lock! lockKey={},expireTime={},timeout={}",lockKey,expireTime,timeout);
        boolean locked = false;
        long now = System.nanoTime();
        do {
            try{
                locked = setNX(lockKey,String.valueOf(now));
                if (locked){
                    expire(lockKey,expireTime);
                    return locked;
                }else {
                    Long expire = ttl(lockKey);
                    if (expire == -1)
                        expire(lockKey,expireTime);
                }
            }catch (Exception e){
                logger.error("操作redis锁失败 lockKey={}",lockKey);
                continue;
            }
        }while (!locked &&(System.nanoTime() - now < timeout * 1000));

        logger.info("lock timeout lockKey={},timeout={}",lockKey,timeout);
        return locked;
    }

    public Boolean readLock(final String lockKey ,final long timeout){
        logger.info("try read lock! lockKey={},timeout={}",lockKey,timeout);
        long now = System.nanoTime();
        do {
            try{
                boolean lock = exists(lockKey);
                logger.info("read lock lockKey={},exists={}",lockKey,lock);
                return lock;
            }catch (Exception e){
                logger.error("操作redis锁失败 lockKey={}",lockKey);
                continue;
            }
        }while (System.nanoTime() - now < timeout * 1000);

        logger.info("read lock timeout lockKey={},timeout={}",lockKey,timeout);
        return false;
    }

    public Boolean unLock(final String lockKey ,final long timeout){
        logger.info("try unlock! lockKey={},timeout={}",lockKey,timeout);
        long now = System.nanoTime();
        do {
            try{
                if( exists(lockKey))
                return delete(lockKey);
            }catch (Exception e){
                logger.error("操作redis锁失败 lockKey={}",lockKey);
                continue;
            }
        }while (System.nanoTime() - now < timeout * 1000);

        logger.info("unlock timeout lockKey={},timeout={}",lockKey,timeout);
        return false;

    }

}
