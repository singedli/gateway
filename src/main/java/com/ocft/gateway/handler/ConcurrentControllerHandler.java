package com.ocft.gateway.handler;

import com.ocft.gateway.common.converter.GatewayContextConverter;
import com.ocft.gateway.entity.GatewayInterface;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: ConcurrentControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:21
 * @Description: 并发类型的处理器
 *
 */
@Component
public class ConcurrentControllerHandler extends AbstractControllerHandler {
    @Override
    public String buildReqHeader() {
        return null;
    }

    @Override
    public String buildReqBody(String body) {
        return null;
    }

    @Override
    public String sendToBacon(String header, String body,GatewayInterface gatewayInterface) {
        //构建线程任务

        //
        return null;
    }

    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return null;
    }
}
