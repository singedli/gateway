package com.ocft.gateway.handler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: PassThroughControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:19
 * @Description: 透传类型的处理器
 */
public class PassThroughControllerHandler extends AbstractControllerHandler {
    @Override
    public String buildReqHeader() {
        return null;
    }

    @Override
    public String buildReqBody(String body) {
        return null;
    }

    @Override
    public String sendToBacon(String header, String body) {
        return null;
    }

    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return null;
    }
}
