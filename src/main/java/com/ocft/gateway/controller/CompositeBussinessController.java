package com.ocft.gateway.controller;

import lombok.extern.slf4j.Slf4j;
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
 * @Description: TODO
 */
@Slf4j
@Controller
public class CompositeBussinessController {

    @RequestMapping(value = "/**", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> compositeHandler(HttpServletRequest request, @RequestBody String body,
                                                HttpServletResponse response) throws Exception{
        return null;
    }
}
