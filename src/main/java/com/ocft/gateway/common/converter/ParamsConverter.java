package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.MessageConverter;
import com.ocft.gateway.mapper.MessageConverterMapper;
import com.ocft.gateway.stateMachine.StateLangGenerator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MessageConverterMapper messageConverterMapper;

    public String convert(Map<String,Object> params,Map<String, Object> context){

        String current = (String) params.get("current");

        MessageConverter messageConverter = messageConverterMapper.selectOne(new QueryWrapper<MessageConverter>()
                .eq("url", StateLangGenerator.getUrlFromEncodeStateName(current))
                .eq("status", "1")
                .eq("is_deleted", "0"));

        Object data = params.get("data");
        JSONObject result1 = null;
        if(data instanceof JSONArray){
            result1 = ((JSONArray) data).getJSONObject(0);
        }else{
            result1 =(JSONObject)data;
        }
        String requestConfig = messageConverter.getRequestConfig();
        String requestStruct = messageConverter.getRequestStruct();
        JSONObject template =JSONObject .parseObject(requestStruct);
        JSONObject param = JsonStructureConverter.convertStructure(result1, template, requestConfig);
        return param.toJSONString();
    }


}
