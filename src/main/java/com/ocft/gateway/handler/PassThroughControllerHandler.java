package com.ocft.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.exception.GwException;
import com.ocft.gateway.utils.HttpUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: PassThroughControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:19
 * @Description: 透传类型的处理器
 */
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

        String reqUrl = buildeUrl(gatewayInterface);

        String backonRes = null;
        if(HttpMethod.GET.matches(gatewayInterface.getHttpMethod())){
            Map<String,Object> queries = JSONObject.parseObject(body, Map.class);
            backonRes = HttpUtil.get(reqUrl,queries);
        }else if(HttpMethod.POST.matches(gatewayInterface.getHttpMethod())){
            backonRes = HttpUtil.postJsonParams(reqUrl, body);
        }else{
            //抛异常，暂不支持的请求类型
            throw new GwException();
        }
        return backonRes;
    }


    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return null;
    }


    private String buildeUrl(GatewayInterface gatewayInterface){
        String system = gatewayInterface.getSystem();
        Backon backon = backonService.getOne(new QueryWrapper<Backon>().eq("system", system));
        Assert.notNull(backon, ResponseEnum.BACKON_NOT_EXIST.getMessage());
        String domain = backon.getDomain();
        String suffix = backon.getSuffix();
        return  domain + gatewayInterface.getBackonUrl() + suffix;
    }

}
