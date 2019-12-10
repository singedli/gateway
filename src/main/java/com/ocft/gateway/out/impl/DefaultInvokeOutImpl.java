package com.ocft.gateway.out.impl;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.out.AbstractBaseOut;
import com.ocft.gateway.utils.HttpUtil;
import com.ocft.gateway.utils.KV;
import com.ocft.gateway.web.dto.InvokeThirdDTO;
import com.ocft.gateway.web.response.HttpResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: Bobby
 * @create: 2019-12-06 16:20
 * @description: 默认sao实现
 **/
@Service("defaultInvokeOut")
public class DefaultInvokeOutImpl extends AbstractBaseOut {
    private static final Logger logger = LoggerFactory.getLogger(DefaultInvokeOutImpl.class);

    private static final String THIRD_RES_CODE = "status";//第三方系统响应码属性名
    private static final String THIRD_RES_MSG = "message";//第三方系统响应信息属性名
    private static final String THIRD_RES_DATA = "data";//第三方系统响应数据属性名
    private static final String THIRD_RES_SUCCESS = "200";//第三方系统响应成功码

    protected Map<String, String> headers = KV.by("Content-Type", "application/json");

    @Override
    protected void handlerRequest(InvokeThirdDTO req) {
        //包装第三方系统响应参数名
        this.setThirdResponseEntry(req, THIRD_RES_CODE, THIRD_RES_MSG, THIRD_RES_DATA, THIRD_RES_SUCCESS);
        //包装请求头信息
        req.setHeader(headers);
        //请求头是否需要加密 TODO
    }


    //post请求处理
    @Override
    protected JSONObject postHandler(InvokeThirdDTO req) {
        logger.info("DefaultInvokeSaoImpl_req:{}", req.toString());
        logger.info("DefaultInvokeSaoImpl_url:{}", req.getBackOnUrl());
        String res;
        try {
            res = HttpUtil.postJsonParams(req.getBackOnUrl(), req.getRequestData());
        } catch (Exception e) {
            logger.error("DefaultInvokeSaoImpl_POST_ERROR", e);
            throw new GatewayException("5000", "DefaultInvokeSaoImpl_postHandler:请求出错");
        }

        if (StringUtils.isBlank(res)) {
            throw new GatewayException("5000", "DefaultInvokeSaoImpl_postHandler:关联方返回数据为空");
        }
        return JSONObject.parseObject(res);
    }


    @Override
    protected HttpResponseModel<Object> handlerResponse(InvokeThirdDTO req, JSONObject res) {
        HttpResponseModel<Object> responseModel = null;
        if (this.isSuccess(req, res)) {
            responseModel = this.success(req, res);
            logger.info("DefaultInvokeSaoImpl_handlerResponse_Success");
        } else {
            responseModel = this.fail(req, res);
            logger.info("DefaultInvokeSaoImpl_handlerResponse_fail");
        }
        return responseModel;
    }

    @Override
    protected JSONObject getHandler(InvokeThirdDTO req) {
        return null;
    }


}
