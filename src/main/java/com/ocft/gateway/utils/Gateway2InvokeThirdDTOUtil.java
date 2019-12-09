package com.ocft.gateway.utils;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.task.ConcurrentInvokeTask;
import com.ocft.gateway.web.dto.InvokeThirdDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: Bobby
 * @create: 2019-12-09 10:48
 * @description: 通过backonInterface转换为InvokeThirdDTO
 **/
public class Gateway2InvokeThirdDTOUtil {

    public static InvokeThirdDTO parseGatewayInterface(ConcurrentInvokeTask task) {

        InvokeThirdDTO thirdDTO = new InvokeThirdDTO();
        //设置请求路径
        thirdDTO.setBackOnUrl(task.getUrl());
        //设置请求头数据
        if (null != task.getHeaders()) {
            thirdDTO.getHeader().putAll(task.getHeaders());
        }
        //设置请求方式
        thirdDTO.setMethod(task.getHttpMethod());
        //请求参数
        if (StringUtils.isNotBlank(task.getRequestBody())) {
            thirdDTO.setRequestData(task.getRequestBody());
        }
        if (null != task.getGetParams()) {
            thirdDTO.setFormParams(JSONObject.toJSONString(task.getGetParams()));
        }
        return thirdDTO;
    }
}
