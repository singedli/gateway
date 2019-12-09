package com.ocft.gateway.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Bobby
 * @create: 2019-12-06 16:34
 * @description: 请求头键值对
 **/
public class KV<K, V> extends HashMap<K, V> {


    private static final long serialVersionUID = -4906491597320982314L;

    public static <K, V> KV<K, V> by(K key, V value) {
        return new KV<K, V>().set(key, value);
    }

    private KV<K, V> set(K key, V value) {
        this.put(key, value);
        return this;
    }

    public KV<K, V> setAll(Map<? extends K, ? extends V> m) {
        this.putAll(m);
        return this;
    }

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
