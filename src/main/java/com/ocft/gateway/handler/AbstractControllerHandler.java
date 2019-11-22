package com.ocft.gateway.handler;

import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.service.IBackonService;
import com.ocft.gateway.service.IGatewayInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: AbstractControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:18
 * @Description: 处理器控制器抽象类
 */
@Component
public abstract class AbstractControllerHandler implements IControllerHandler {

    @Autowired
    protected IBackonService backonService;

    @Autowired
    protected IBackonInterfaceService backonInterfaceService;

    @Autowired
    protected IGatewayInterfaceService gatewayInterfaceService;

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
    public abstract String sendToBacon(String header, String body,GatewayInterface gatewayInterface);

    /**
     * 返回前端处理
     *
     * @param resout
     * @return
     */
    public abstract Map<String, Object> retToClient(String resout, HttpServletRequest request);


    @Override
    public Map<String, Object> handle(String body, HttpServletRequest request, HttpServletResponse response,GatewayInterface gatewayInterface) throws Exception {
        String requestHeader = this.buildReqHeader();
        String requestBody = this.buildReqBody(body);
        String responseString = sendToBacon(requestHeader, requestBody,gatewayInterface);
        return retToClient(responseString, request);
    }
}
