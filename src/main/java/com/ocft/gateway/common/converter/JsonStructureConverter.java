package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.evaluator.JsonOperateEvalutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
        String jsonA = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\"},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"}]}";
        String jsonB = "{\"url123\":\"http://www.bejson.com\",\"page123\":88,\"address\":{\"city123\":\"江苏苏州\"},\"links\":[{\"name123\":\"Google\"},{\"name123\":\"Baidu\"}]}";
        String path = "url=url123,page=page123,address.city=city123,links.name=name123";
        JSONObject jsonObject = convertStructure(jsonA, jsonB, path);
        System.out.println(JSONObject.toJSONString(jsonObject));
    }

    public static JSONObject convertStructure(String jsonA, String jsonB,String path){
        if(!StringUtils.hasText(jsonA) || !StringUtils.hasText(jsonB) || !StringUtils.hasText(path)) {
            log.error("参数不合法! jsonA:{},jsonB:{},path:{}",jsonA,jsonB,path);
            return null;
        }
        JSONObject jsonObjectA = JSONObject.parseObject(jsonA);
        JSONObject jsonObjectB = JSONObject.parseObject(jsonB);
        return convertStructure(jsonObjectA,jsonObjectB,path);
    }

    public static JSONObject convertStructure(JSONObject jsonA, JSONObject jsonB,String path){
        if(ObjectUtils.isEmpty(jsonA) ||ObjectUtils.isEmpty(jsonB) || !StringUtils.hasText(path)){
            log.error("参数不合法! jsonA:{},jsonB:{},path:{}",jsonA,jsonB,path);
            return null;
        }
        Properties properties = new Properties();
        path = path.replaceAll(",", "\n");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(path.getBytes());
        try {
            properties.load(byteArrayInputStream);
        } catch (IOException e) {
            log.error("加载key路径发生异常,异常信息为{}", e);
        }

        Set<String> set = properties.stringPropertyNames();
        for (String key : set) {
            String value = properties.getProperty(key);
            convertStructure(jsonA, jsonB,key,value);
        }
        return jsonB;
    }


        /**
         *
         * @param jsonA 源json对象
         * @param jsonB 目标json对象
         * @param pathA 要转换的key在源json对象中的路径
         * @param pathB 要转换的key在目标json对象中的路径
         */
    public static void convertStructure(JSONObject jsonA, JSONObject jsonB, String pathA, String pathB) {
        Object value = JsonOperateEvalutor.getJsonPropertyValue(jsonA, pathA);
        JsonOperateEvalutor.putJsonPropertyValue(jsonB,pathB,value);
    }
}
