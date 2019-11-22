package com.ocft.gateway.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.HandlerType;
import com.ocft.gateway.service.IGatewayInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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

    private HandlerType getType(String uri){
        GatewayInterface one = gatewayInterfaceService.getOne(new QueryWrapper<GatewayInterface>().eq("url", uri).eq("status", "1"));
        String type = one.getType();
        return HandlerType.valueOf(type);
    }

    protected HandlerType getType(HttpServletRequest req){
        String uri = this.getURI(req);
        return this.getType(uri);
    }
}
