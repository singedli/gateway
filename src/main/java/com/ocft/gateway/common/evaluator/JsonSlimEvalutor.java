package com.ocft.gateway.common.evaluator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.parser.JsonSlimParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author lijiaxing
 * @Title: JsonSlimEvalutor
 * @ProjectName gateway
 * @date 2019/11/23下午5:05
 * @Description: json 剪裁处理程序
 *
 *
 */
public class JsonSlimEvalutor {
    private static final String INNER_SPLIT_FLAG = ",";

    public static void main(String[] args) {
        String json = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\"},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}";
        String path = "links.url,address.city,name";
        JSONObject jsonObject = JSONObject.parseObject(json);
        remove(jsonObject,path);
        System.out.println(jsonObject);

    }

    /**
     *
     * @param t 要剪裁的json对象，可以是JSONObject，也可以是JSONArray
     * @param paths 要剪裁的key的路径
     *
     *      例如json字符串为：
     *              {
     *                  "name": "BeJson",
     *                  "url": "http://www.bejson.com",
     *                  "page": 88,
     *                  "address": {
     *                      "street": "科技园路.",
     *                      "city": "江苏苏州",
     *                      "country": "中国"
     *                  },
     *                  "links": [
     *                      {
     *                          "name": "Google",
     *                          "url": "http://www.google.com"
     *                      },
     *                      {
     *                          "name": "Baidu",
     *                          "url": "http://www.baidu.com"
     *                      }
     *                  ]
     *              }
     *      1、删除单个属性：例如要将json中的url字段删除掉，则paths为"url"
     *         删除多个属性：例如要将json中的url和page字段同事删除掉，则paths为"url,page"
     *      2、删除json对象中的对象：例如要将json中的address对象的city字段删除掉，则paths为"address.city"
     *      3、删除json对象中的数组：例如要将json中的links数组中每一个对象的name字段删除掉，则path为"links.name"
     *
     * @return
     */
    public static <T extends JSON> T remove(T t, String paths){
        String[] split = paths.split(INNER_SPLIT_FLAG);
        for (String path : split) {
            deleteJsonProperty(t, path);
        }
        return t;
    }

    public static <T extends JSON> T retain(T t, String paths){
        Map<String, Object> pathMap = JsonSlimParser.pathStr2Map(paths);
        TreeSet<String> resultSet = new TreeSet<>();
        JsonSlimParser.getKeepStylePath("",pathMap,resultSet);
        keepKeysByGivenKeySet(resultSet,t);
        return t;
    }

    /**
     * 递归删除指定字段
     *
     * @param outterObject
     * @param source
     * @return
     */
    private static void deleteJsonProperty(Object outterObject, String source) {
        if (outterObject == null || source == null || "".equals(source))
            return;
        if (outterObject instanceof JSONObject) {
            JSONObject tempJson = (JSONObject) outterObject;
            if (source.contains(".")) {
                String arg = source.substring(0, source.indexOf('.'));
                deleteJsonProperty(tempJson.get(arg), source.substring(source.indexOf('.') + 1));
                return;
            }
            tempJson.remove(source);
        } else if (outterObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) outterObject;
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i) instanceof JSONArray) {
                    JSONArray tempJson = (JSONArray) tempArray.get(i);
                    deleteJsonProperty(tempJson, source);
                } else {
                    JSONObject tempJson = (JSONObject) tempArray.get(i);
                    if (source.contains(".")) {
                        String arg = source.substring(0, source.indexOf('.'));
                        if (tempJson.get(arg) == null)
                            continue;
                        deleteJsonProperty(tempJson.get(arg), source.substring(source.indexOf('.') + 1));
                    } else {
                        tempJson.remove(source);
                    }
                }
            }
        }
    }


    /**
     * 递归实现字段保留功能
     *
     * @param outterObject
     * @param source
     */
    private static void retainJsonProperty(Object outterObject, String source) {
        if (outterObject == null || source == null || "".equals(source))
            return;

        if (outterObject instanceof JSONObject) {
            JSONObject obj = ((JSONObject) outterObject);
            if (source.contains(".")) {
                String arg = source.substring(0, source.indexOf('.'));
                retainJsonProperty(obj.get(arg), source.substring(source.indexOf('.') + 1));
            } else
                JsonSlimParser.keepJsonObjectByGivenKeys(obj, source);
        } else if (outterObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) outterObject;
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i) instanceof JSONObject) {
                    JSONObject obj = tempArray.getJSONObject(i);
                    if (source.contains(".")) {
                        String arg = source.substring(0, source.indexOf('.'));
                        retainJsonProperty(obj.get(arg), source.substring(source.indexOf('.') + 1));
                    } else {
                        JsonSlimParser.keepJsonObjectByGivenKeys(obj, source);
                    }
                } else {
                    JSONArray jsonArray = (JSONArray) tempArray.get(i);
                    retainJsonProperty(jsonArray, source);
                }
            }
        }
    }

    public static void keepKeysByGivenKeySet(TreeSet<String> keySet, Object obj){
        if(!(keySet instanceof TreeSet))
            return;
        for(String key:keySet){
            retainJsonProperty(obj,key);
        }
    }
}
