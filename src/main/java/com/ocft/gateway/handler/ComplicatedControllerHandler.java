package com.ocft.gateway.handler;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: ComplicatedControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:22
 * @Description: 复杂类型处理器
 */
@Component
public class ComplicatedControllerHandler extends AbstractControllerHandler {
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
