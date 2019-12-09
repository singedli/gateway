package com.ocft.gateway.sao;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.web.dto.InvokeThirdDTO;
import com.ocft.gateway.web.response.HttpResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Bobby
 * @create: 2019-12-06 14:22
 * @description: 抽象调用接口类
 **/

public abstract class AbstractBaseSao {


    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseSao.class);


    public HttpResponseModel<Object> invokeHandler(InvokeThirdDTO req) {
        //默认请求方式为post
        if (null == req.getMethod()) {
            req.setMethod("POST");
        }

        JSONObject result = new JSONObject();
        HttpResponseModel<Object> responseModel = null;

        switch (req.getMethod()) {
            case "POST":
                try {
                    handlerRequest(req);//处理请求参数
                } catch (Exception e) {
                    error(e);
                }


                try {
                    result = postHandler(req);//post调用第三方接口
                } catch (Exception e) {
                    error(e);
                }

                try {
                    responseModel = handlerResponse(req, result);
                } catch (Exception e) {

                }


                break;

            case "GET":

                try {
                    handlerRequest(req);//处理请求参数
                } catch (Exception e) {
                    error(e);
                }


                try {
                    result = getHandler(req);//get调用第三方接口
                } catch (Exception e) {
                    error(e);
                }

                try {
                    responseModel = handlerResponse(req, result);
                } catch (Exception e) {

                }
                break;
            default:
                break;
        }

        return responseModel;

    }


    void error(Exception e) {
        logger.error("AbstraccBaseSao发生异常", e.getMessage());
        throw new GatewayException("5000", "AbstraccBaseSao发生异常");
    }


    //设置关联方的返回数据类型
    protected void setThirdResponseEntry(InvokeThirdDTO req, String code, String msg, String data, String successCode) {
        req.setCode(code);
        req.setMsg(msg);
        req.setData(data);
        req.setSuccess(successCode);

    }


    //判断是否请求成功
    protected boolean isSuccess(InvokeThirdDTO req, JSONObject res) {
        if (null == res) {
            error(new GatewayException("500", "AbstraccBaseSao_isSuccess_res_isNull"));
        }
        String code = res.getString(req.getCode());
        return req.getSuccess().equalsIgnoreCase(code);
    }

    // 请求成功封装返回参数
    protected HttpResponseModel<Object> success(InvokeThirdDTO req, JSONObject res) {
        Object o = res.get(req.getData());
        logger.info(JSONObject.toJSONString(o));
        return new HttpResponseModel<>(o);
    }

    // 请求失败成功封装返回参数
    protected HttpResponseModel<Object> fail(InvokeThirdDTO req, JSONObject res) {
        String code = StringUtils.defaultString(res.getString(req.getData()), ResponseEnum.FAIL.getCode());
        String msg = StringUtils.defaultString(res.getString(req.getData()), ResponseEnum.FAIL.getMessage());
        return new HttpResponseModel<>(code, msg);
    }


    abstract protected void handlerRequest(InvokeThirdDTO req);

    abstract protected HttpResponseModel<Object> handlerResponse(InvokeThirdDTO req, JSONObject res);

    abstract protected JSONObject postHandler(InvokeThirdDTO req);

    abstract protected JSONObject getHandler(InvokeThirdDTO req);

}
