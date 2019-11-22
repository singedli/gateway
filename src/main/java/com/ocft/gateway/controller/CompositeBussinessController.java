package com.ocft.gateway.controller;

import com.ocft.gateway.cache.HandlerTypeCache;
import com.ocft.gateway.enums.HandlerType;
import com.ocft.gateway.handler.IControllerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Map<String, Object> compositeHandler(HttpServletRequest request, @RequestBody String body,
                                                  HttpServletResponse response) throws Exception{
        HandlerType type = getType(request);

        IControllerHandler handler = handlerTypeCache.getHandler(type);
        Map<String, Object> result = null;
        try{
            result = handler.handle(body, request, response);
        }catch (Exception e){
            throw e;
        }
        return result;
    }
}
