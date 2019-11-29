package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/29 15:49
 * @Description:
 */
public class JsonCacheDataConverter {

    public static void main(String[] args) {
        String json = "{\"data\":[{\"city\":\"上海\",\"name\":\"Mayo Clinic医疗模式国际研讨会\",\"startTime\":\"2019-08-13\",\"id\":3},{\"city\":\"上海\",\"name\":\"药物警戒培训班\",\"startTime\":\"2019-08-13\",\"id\":4},{\"city\":\"上海\",\"name\":\"微流控大会\",\"startTime\":\"2019-08-13\",\"id\":5},{\"city\":\"上海\",\"name\":\"临床质谱与高端医学检验发展论坛\",\"startTime\":\"2019-08-13\",\"id\":6},{\"city\":\"北京\",\"name\":\"液体活检产业发展论坛\",\"startTime\":\"2019-08-02\",\"id\":7}]}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        Object dataByNum = getCacheData(jsonObject, 2);
        System.out.println(dataByNum);

    }

    public static Object getCacheData(JSONObject data,int num){
        Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator();
        JSONObject jsonObject = new JSONObject();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            if (data.get(next.getKey()) instanceof JSONArray){
                JSONArray jsonArray = data.getJSONArray(next.getKey());
                if (jsonArray.size() > num){
                    jsonObject.put(next.getKey(),jsonArray.subList(0,num));
                }else {
                    jsonObject.put(next.getKey(),jsonArray);
                }
            } if (data.get(next.getKey()) instanceof JSONObject){
                jsonObject.put(next.getKey(),next.getValue());
            }
        }
        return jsonObject;
    }
}
