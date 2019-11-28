package com.ocft.gateway.common.parser;

import com.alibaba.fastjson.JSONObject;
import java.util.*;

/**
 * @author lijiaxing
 * @Title: JsonSlimParser
 * @ProjectName gateway
 * @date 2019/11/23下午5:05
 * @Description: json剪裁解析器
 */
public class JsonSlimParser {
    private static final String INNER_SPLIT_FLAG = ",";

    /**
     * @param jsonObj given manage jsonObejct
     * @param keys    given keep keys
     *                同一级的多个字段，用，隔开
     */
    public static void keepJsonObjectByGivenKeys(JSONObject jsonObj, String keys) {
        String[] keyAttr = keys.split(INNER_SPLIT_FLAG);
        Set<String> keyKeepSet = new HashSet<String>();
        for (String s : keyAttr)
            keyKeepSet.add(s);
        Set<String> keyRemove = new HashSet<String>();
        keyRemove.addAll(jsonObj.keySet());
        keyRemove.removeAll(keyKeepSet);
        for (String key : keyRemove)
            jsonObj.remove(key);
    }

    /***
     * pathStrt
     * @param pathStr
     * @return
     */
    public static Map<String, Object> pathStr2Map(String pathStr) {
        Map<String, Object> pathMap = new HashMap<String, Object>();
        String[] paths = pathStr.split(",");
        for (String path : paths) {
            String[] tmp = path.split("\\.");
            if (tmp.length == 1) {
                pathMap.put(tmp[0], null);
            }
            Map<String, Object> tmpMap = pathMap;
            for (int i = 0; i < tmp.length; i++) {
                if (tmpMap.get(tmp[i]) != null)
                    tmpMap = (HashMap<String, Object>) tmpMap.get(tmp[i]);
                else if (i < tmp.length) {
                    tmpMap.put(tmp[i], new HashMap<String, Object>());
                    tmpMap = (HashMap<String, Object>) tmpMap.get(tmp[i]);
                } else
                    tmpMap.put(tmp[i], null);
            }
        }
        return pathMap;
    }

    public static Map<String, Object> pathStr2Map(String pathStr, String value) {
        Map<String, Object> pathMap = new HashMap<>();
        String[] paths = pathStr.split(",");
        for (String path : paths) {
            String[] tmp = path.split("\\.");
            if (tmp.length == 1) {
                pathMap.put(tmp[0], value);
                return pathMap;
            }
            Map<String, Object> tmpMap = pathMap;
            for (int i = 0; i < tmp.length; i++) {
               if (i < tmp.length) {
                    if(i+1 == tmp.length){
                        tmpMap.put(tmp[i], value);
                    }else{
                        tmpMap.put(tmp[i], new HashMap<String, Object>());
                        tmpMap = (HashMap<String, Object>) tmpMap.get(tmp[i]);
                    }
                }
            }
        }
        return pathMap;
    }

    /**
     * address.street=street111, links.url=url111
     *
     * @param propertyString
     * @return
     */



    /**
     * 获取路径方法
     *
     * @param path
     * @param map
     */
    public static void getKeepStylePath(String path, Map<String, Object> map, Set<String> resultSet) {
        if (map == null || map.size() == 0) {
            return;
        }
        if ("".equals(path)) {
            resultSet.add(getKeySpiltBy(map));
            path = "";
        } else {
            resultSet.add(path + "." + getKeySpiltBy(map));
            path += ".";
        }
        for (String key : map.keySet()) {
            getKeepStylePath(path + key, (Map<String, Object>) map.get(key), resultSet);
        }
    }

    /**
     * 获取key打印，用逗号隔开
     *
     * @param map
     * @return
     */
    public static String getKeySpiltBy(Map<String, Object> map) {
        if (map == null || map.size() == 0)
            return "";
        Set<String> set = map.keySet();
        String resultStr = "";
        for (String key : set) {
            resultStr += key;
            resultStr += ",";
        }
        return resultStr.substring(0, resultStr.length() - 1);
    }
}
