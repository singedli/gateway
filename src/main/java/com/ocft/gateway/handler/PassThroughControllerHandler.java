package com.ocft.gateway.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ErrorCode;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.out.AbstractBaseOut;
import com.ocft.gateway.utils.HttpUtil;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.dto.InvokeThirdDTO;
import com.ocft.gateway.web.response.HttpResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: PassThroughControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:19
 * @Description: 透传类型的处理器
 */
@Slf4j
@Component
public class PassThroughControllerHandler extends AbstractControllerHandler {
    @Autowired
    private AbstractBaseOut httpOut;

    @Override
    public String buildReqHeader() {
        return "";
    }

    @Override
    public String buildReqBody(String body) {
        return body;
    }

    @Override
    public String sendToBacon(GatewayContext gatewayContext) {
        GatewayInterface gatewayInterface = gatewayContext.getGatewayInterface();

        String requestBody = gatewayContext.getRequestBody();

        String backonSystemAndUrl = gatewayInterface.getBackonUrl();
        JSONArray jsonArray = JSONArray.parseArray(backonSystemAndUrl);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String system = jsonObject.getString("system");
        Assert.hasText(system,ResponseEnum.BACKON_NOT_EXIST.getMessage());
        String backonUrl = jsonObject.getString("backonUrl");
        Assert.hasText(backonUrl,ResponseEnum.BACKON_INTERFACE_NOT_EXIST.getMessage());

        Backon backon = backonService.getOne(new QueryWrapper<Backon>().eq("system", system).eq("status","1").eq("is_deleted","0"));
        Assert.notNull(backon, ResponseEnum.BACKON_NOT_EXIST.getMessage());

        String domain = backon.getDomain();
        String suffix = backon.getSuffix();
        String reqUrl = domain + backonUrl + suffix;
        String httpMethod = backonInterfaceService.getBackonInterfaceMethod(backonUrl);
        InvokeThirdDTO invokeThirdDTO = new InvokeThirdDTO()
                .setBackOnUrl(reqUrl)
                .setMethod(httpMethod)
                .setRequestData(requestBody)
                .setCode(backon.getSuccessCode())
                .setSuccess(backon.getSuccessValue());

        HttpResponseModel<Object> objectHttpResponseModel = httpOut.invokeHandler(invokeThirdDTO);
//        String result = "";
//        if(ErrorCode.SYSTEM_SUCCESS.getCode().equalsIgnoreCase(objectHttpResponseModel.getCode())){
//            JSONObject resultObj = (JSONObject)objectHttpResponseModel.getData();
//            result = resultObj.toJSONString();
//        }
        return JSONObject.toJSONString(objectHttpResponseModel);
    }


    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return ResultUtil.createResult(resout);
    }

}
