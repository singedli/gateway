package com.ocft.gateway.utils;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.enums.ResponseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: ResultUtil
 * @ProjectName gateway
 * @date 2019/11/28下午5:30
 * @Description: 返回值配置
 */
public class ResultUtil {

    public static Map<String,Object> createResult(Object data{
        Map<String, Object> map = new HashMap<>();
        map.put("code",ResponseEnum.SUCCESS.getCode());
        map.put("msg",ResponseEnum.SUCCESS.getMessage());
        map.put("data",data);
        return map;
    }

    public static Map<String,Object> bizExceptionResult(GatewayException e){
        Map<String, Object> map = new HashMap<>();
        map.put("code",e.getCode());
        map.put("msg",e.getMessage());
        return map;
    }

    public static Map<String,Object> exceptionResult(){
        Map<String, Object> map = new HashMap<>();
        map.put("code",ResponseEnum.FAIL.getCode());
        map.put("msg",ResponseEnum.FAIL.getMessage());
        return map;
    }
}
