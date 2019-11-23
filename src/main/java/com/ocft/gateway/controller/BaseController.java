package com.ocft.gateway.controller;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IGatewayInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijiaxing
 * @Title: BaseController
 * @ProjectName gateway
 * @date 2019/11/22上午11:08
 * @Description:
 */
@Slf4j
@Service
public class BaseController {

    @Autowired
    private IGatewayInterfaceService gatewayInterfaceService;

    /**
     *
     * @param req
     * @return
     */
    private String getURI(HttpServletRequest req){
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        return requestURI.replace(contextPath,"");
    }

    private GatewayInterface getGateWayInterface(String uri){
        return gatewayInterfaceService.getGateWayInterface(uri);
    }

    protected GatewayInterface getGateWayInterface(HttpServletRequest req){
        String uri = this.getURI(req);
        return this.getGateWayInterface(uri);
    }

    protected GatewayContext buildGatewayContext(HttpServletRequest request, HttpServletResponse response,String requestBody,GatewayInterface gateWayInterface){
        return new GatewayContext().setRequest(request).setResponse(response).setRequestBody(requestBody).setGatewayInterface(gateWayInterface);
    }
}
