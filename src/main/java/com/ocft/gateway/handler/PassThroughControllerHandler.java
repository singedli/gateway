package com.ocft.gateway.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.utils.OkHttpUtil;
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
        String system = gatewayInterface.getSystem();
        Backon backon = backonService.getOne(new QueryWrapper<Backon>().eq("systemt", system));
        Assert.notNull(backon,"");
        String domain = backon.getDomain();
        String suffix = backon.getSuffix();
        String reqUrl = domain + gatewayInterface.getBackonUrl() + suffix;

        String backonRes = null;

        if(HttpMethod.GET.matches(gatewayInterface.getHttpMethod())){

        }else if(HttpMethod.POST.matches(gatewayInterface.getHttpMethod())){
            backonRes = OkHttpUtil.postJsonParams(reqUrl, body);
        }else{
            //抛异常，暂不支持的请求类型
        }

        return backonRes;
    }


    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return null;
    }



}
