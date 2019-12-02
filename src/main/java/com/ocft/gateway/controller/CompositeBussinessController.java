package com.ocft.gateway.controller;

import com.ocft.gateway.cache.HandlerTypeCache;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.HandlerType;
import com.ocft.gateway.handler.IControllerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: CompositeBussinessController
 * @ProjectName gateway
 * @date 2019/11/21下午6:12
 * @Description: 主控制器
 *
 * 所有的接口请求都由主控制器根据配置分发给对应的Handler处理
 */
@Slf4j
@Controller
public class CompositeBussinessController extends BaseController {

    @Autowired
    private HandlerTypeCache handlerTypeCache;

    @RequestMapping(value = "/**", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> compositeHandler(HttpServletRequest request, @RequestBody String body, HttpServletResponse response) throws Exception{
        GatewayInterface gateWayInterface = super.getGateWayInterface(request);
        GatewayCache gatewayCache = super.getGatewayCache(request);
        String type = gateWayInterface.getType();
        IControllerHandler handler = handlerTypeCache.getHandler(HandlerType.valueOf(type));
        return handler.handle(super.buildGatewayContext(request, response, body, gateWayInterface,gatewayCache));
    }

    @RequestMapping(value = "/**", method = { RequestMethod.GET }, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> compositeHandler(HttpServletRequest request, @RequestParam Map<String,Object> map, HttpServletResponse response) throws Exception{
        return null;
    }
}
