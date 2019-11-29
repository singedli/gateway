package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ocft.gateway.common.evaluator.JsonSlimEvalutor;
import com.ocft.gateway.common.parser.JsonSlimParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author lijiaxing
 * @Title: JsonStructureConverter
 * @ProjectName gateway
 * @date 2019/11/29上午9:16
 * @Description: json结构转换器
 */
@Slf4j
public class JsonStructureConverter {

    public static void main(String[] args) {
        String jsonA = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\"},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}";
        String jsonB = "{\"name\":\"BeJson\",\"isNonProfit\":true,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\",\"url\":\"\",\"page\":0},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}";
        String path = "url=address.url,page=address.page";
        Properties properties = new Properties();
        path = path.replaceAll(",", "\n");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(path.getBytes());
        try {
            properties.load(byteArrayInputStream);
        } catch (IOException e) {
            log.error("加载key路径发生异常,异常信息为{}", e);
        }
        JSONObject jsonObjectA = JSONObject.parseObject(jsonA);
        JSONObject jsonObjectB = JSONObject.parseObject(jsonB);
        Set<String> set = properties.stringPropertyNames();
        for (String key : set) {
            String value = properties.getProperty(key);
            copyValue(jsonObjectA, jsonObjectB,key,value);
        }
        System.out.println(JSONObject.toJSONString(jsonObjectB,SerializerFeature.PrettyFormat));

    }

    private static void copyValue(JSONObject jsonA, JSONObject jsonB, String pathA, String pathB) {
        Object value = getValue(jsonA, pathA);
        putValue(jsonB,pathB,value);
        Object value1 = getValue(jsonB, pathB);
        System.out.println(value1);
    }

    private static Object getValue(Object jsonObject, String path) {
        if (jsonObject == null || !StringUtils.hasText(path))
            return null;

        if (jsonObject instanceof JSONObject) {
            JSONObject tempJson = (JSONObject) jsonObject;
            if (path.contains(".")) {
                String arg = path.substring(0, path.indexOf('.'));
                return getValue(tempJson.get(arg), path.substring(path.indexOf('.') + 1));
            }
            return tempJson.get(path);
        } else {
            JSONArray tempArray = (JSONArray) jsonObject;
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i) instanceof JSONArray) {
                    JSONArray tempJson = (JSONArray) tempArray.get(i);
                    return getValue(tempJson, path);
                } else {
                    JSONObject tempJson = (JSONObject) tempArray.get(i);
                    if (path.contains(".")) {
                        String arg = path.substring(0, path.indexOf('.'));
                        if (tempJson.get(arg) == null)
                            continue;
                        return getValue(tempJson.get(arg), path.substring(path.indexOf('.') + 1));
                    } else {
                        return tempJson.get(path);
                    }
                }
            }
        }
        return null;
    }

    private static void putValue(Object jsonObject, String path, Object value) {
        if (jsonObject == null || !StringUtils.hasText(path) || value == null)
            return;

        if (jsonObject instanceof JSONObject) {
            JSONObject tempJson = (JSONObject) jsonObject;
            if (path.contains(".")) {
                String arg = path.substring(0, path.indexOf('.'));
                putValue(tempJson.get(arg), path.substring(path.indexOf('.') + 1), value);
                return;
            }
            tempJson.put(path, value);
        } else if (jsonObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) jsonObject;
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i) instanceof JSONArray) {
                    JSONArray tempJson = (JSONArray) tempArray.get(i);
                    putValue(tempJson, path, value);
                } else {
                    JSONObject tempJson = (JSONObject) tempArray.get(i);
                    if (path.contains(".")) {
                        String arg = path.substring(0, path.indexOf('.'));
                        if (tempJson.get(arg) == null)
                            continue;
                        putValue(tempJson.get(arg), path.substring(path.indexOf(".") + 1), value);
                    } else {
                        tempJson.put(path, value);
                    }
                }
            }
        }
    }
}
