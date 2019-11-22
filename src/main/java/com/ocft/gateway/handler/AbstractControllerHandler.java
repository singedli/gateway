package com.ocft.gateway.handler;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: AbstractControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:18
 * @Description: 抽象处理器控制器类
 */
@Component
public abstract class AbstractControllerHandler implements IControllerHandler {

    /**
     * 构造请求报文头
     *
     * @return
     */
    public abstract String buildReqHeader();

    /**
     * 构造请求报文体
     *
     * @param body
     * @return
     */
    public abstract String buildReqBody(String body);

    /**
     * 发送报文给后台
     *
     * @param header
     * @param body
     * @return
     */
    public abstract String sendToBacon(String header, String body);

    /**
     * 返回前端处理
     *
     * @param resout
     * @return
     */
    public abstract Map<String, Object> retToClient(String resout, HttpServletRequest request);


    @Override
    public Map<String, Object> handle(String body, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestHeader = this.buildReqHeader();
        String requestBody = this.buildReqBody(body);
        String responseString = sendToBacon(requestHeader, requestBody);
        return retToClient(responseString, request);
    }
}
