package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author lijiaxing
 * @Title: JsonPropertiesConverter
 * @ProjectName gateway
 * @date 2019/11/27下午5:23
 * @Description: json属性名转换器
 */
@Slf4j
public class JsonPropertiesConverter {
    /**
     * @param outterObject   要转换的json对象
     * @param propertyString 配置字符串，多个用逗号隔开
     *
     *  例如json字符串为：
     *  {
     *     "name": "BeJson",
     *     "url": "http://www.bejson.com",
     *     "page": 88,
     *     "address": {
     *         "street": "科技园路.",
     *         "city": "江苏苏州",
     *         "country": "中国"
     *     },
     *     "links": [
     *         {
     *             "name": "Google",
     *             "url": "http://www.google.com"
     *         },
     *         {
     *             "name": "Baidu",
     *             "url": "http://www.baidu.com"
     *         }
     *     ]
     *  }
     *  1、  替换换单个key：例如要将json中的url字段替换为url123，则propertyString为"url=url123"
     *  2、  替换多个属性：例如要将json中的url和page字段分别替换为url123和page123，则propertyString为"url=url123,page=page123"
     *  3、  替换json对象中的对象：例如要将json中的address对象的city字段替换为city123，则propertyString为"address.city=city123"
     *  4、  替换json对象中的数组：例如要将json中的links数组中每一个对象的name字段都替换为name123，则propertyString为"links.name=name123"
     */
    public static void convertJsonProperty(Object outterObject, String propertyString) {
        log.info("转换前的json为{},配置为{}", outterObject, propertyString);
        Properties properties = new Properties();
        propertyString = propertyString.replaceAll(",", "\n");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(propertyString.getBytes());
        try {
            properties.load(byteArrayInputStream);
        } catch (IOException e) {
            log.error("加载key路径发生异常,异常信息为{}", e);
        }
        Set<String> set = properties.stringPropertyNames();
        for (String oldKey : set) {
            String newKey = properties.getProperty(oldKey);
            convertJsonProperty(outterObject, oldKey, newKey);
        }
        log.info("转换后的json为{}", outterObject);
    }

    private static void convertJsonProperty(Object outterObject, String oldKey, String newKey) {
        if (outterObject == null || !StringUtils.hasText(oldKey) || !StringUtils.hasText(newKey))
            return;

        if (outterObject instanceof JSONObject) {
            JSONObject tempJson = (JSONObject) outterObject;
            if (oldKey.contains(".")) {
                String arg = oldKey.substring(0, oldKey.indexOf('.'));
                convertJsonProperty(tempJson.get(arg), oldKey.substring(oldKey.indexOf('.') + 1), newKey);
                return;
            }
            replaceKey(tempJson, oldKey, newKey);
        } else if (outterObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) outterObject;
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i) instanceof JSONArray) {
                    JSONArray tempJson = (JSONArray) tempArray.get(i);
                    convertJsonProperty(tempJson, oldKey, newKey);
                } else {
                    JSONObject tempJson = (JSONObject) tempArray.get(i);
                    if (oldKey.contains(".")) {
                        String arg = oldKey.substring(0, oldKey.indexOf('.'));
                        if (tempJson.get(arg) == null)
                            continue;
                        convertJsonProperty(tempJson.get(arg), oldKey.substring(oldKey.indexOf('.') + 1), newKey);
                    } else {
                        replaceKey(tempJson, oldKey, newKey);
                    }
                }
            }
        }
    }

    private static void replaceKey(JSONObject jsonObject, String oldKey, String newKey) {
        Object value = jsonObject.get(oldKey);
        jsonObject.remove(oldKey);
        jsonObject.put(newKey, value);
    }

}
