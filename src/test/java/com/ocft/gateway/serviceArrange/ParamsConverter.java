package com.ocft.gateway.serviceArrange;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.converter.JsonStructureConverter;
import com.ocft.gateway.common.evaluator.JsonOperateEvalutor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lijiaxing
 * @Title: ParamsConverter
 * @ProjectName gateway
 * @date 2019/12/9下午4:46
 * @Description: TODO
 */
@Component
public class ParamsConverter {

    public String convert(JSONObject jsonObjectA, JSONObject jsonObjectB, String path) {
        JSONObject convertedStructure = JsonStructureConverter.convertStructure(jsonObjectA, jsonObjectB, path);
        return JSONObject.toJSONString(convertedStructure);
    }

    public String convert(Map<String,Object> params){
        System.out.println(params);
        Object data = params.get("data");
        JSONObject result1 = null;
        if(data instanceof JSONArray){
            result1 = ((JSONArray) data).getJSONObject(0);
        }else{
            result1 =(JSONObject)data;
        }
        String path = "moduleId=id";
        //Integer value = (Integer)JsonOperateEvalutor.getJsonPropertyValue(result1, path);
        String templateString  = "{\"id\":\"\"}";
        JSONObject template = JSONObject.parseObject(templateString);
        JSONObject param = JsonStructureConverter.convertStructure(result1, template, path);
        System.out.println(JSONObject.toJSONString(param));
        return param.toJSONString();
    }
}
