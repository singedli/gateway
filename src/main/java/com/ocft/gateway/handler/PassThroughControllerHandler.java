package com.ocft.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public String buildReqHeader() {
        return "";
    }

    @Override
    public String buildReqBody(String body) {
        return body;
    }

    @Override
    public String sendToBacon(String header, String body, GatewayInterface gatewayInterface) {
        String reqUrl = buildUrl(gatewayInterface);

        String backonRes = null;
        String httpMethod = backonInterfaceService.getBackonInterfaceMethod(gatewayInterface.getBackonUrl());
        if (HttpMethod.GET.matches(httpMethod)) {
            Map<String, Object> queries = JSONObject.parseObject(body, Map.class);
            backonRes = HttpUtil.get(reqUrl, queries);
        } else if (HttpMethod.POST.matches(httpMethod)) {
            backonRes = HttpUtil.postJsonParams(reqUrl, body);
        } else {
            throw new GatewayException(ResponseEnum.HTTP_METHOD_NOT_EXIST_SUPPORTED);
        }
        return backonRes;
    }


    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return null;
    }


    private String buildUrl(GatewayInterface gatewayInterface) {
        log.info("构建请求后台接口url，网关接口为：{}", gatewayInterface);
        String system = gatewayInterface.getSystem();
        Backon backon = backonService.getOne(new QueryWrapper<Backon>().eq("system", system).eq("status","1").eq("is_deleted","0"));
        Assert.notNull(backon, ResponseEnum.BACKON_NOT_EXIST.getMessage());
        String domain = backon.getDomain();
        String suffix = backon.getSuffix();
        return domain + gatewayInterface.getBackonUrl() + suffix;
    }

}
