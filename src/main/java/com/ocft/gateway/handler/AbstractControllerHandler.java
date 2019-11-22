package com.ocft.gateway.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: AbstractControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:18
 * @Description:
 */
public class AbstractControllerHandler implements IControllerHandler {
    @Override
    public Map<String, Object> handle(String body, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
