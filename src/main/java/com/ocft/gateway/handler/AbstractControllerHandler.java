package com.ocft.gateway.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.service.IBackonService;
import com.ocft.gateway.service.IGatewayInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
    public Map<String, Object> handle(GatewayContext gatewayContext) throws Exception {
        String requestHeader = this.buildReqHeader();
        String requestBody = this.buildReqBody(gatewayContext.getRequestBody());
        String responseString = this.sendToBacon(requestHeader, requestBody,gatewayContext.getGatewayInterface());
        return this.retToClient(responseString, gatewayContext.getRequest());
    }

    public Map<String, Object> handle(List<GatewayContext> gatewayContexts) throws Exception{
        for (GatewayContext gatewayContext : gatewayContexts) {
            //String requestHeader = this.buildReqHeader();
        }
        return null;
    }
}
