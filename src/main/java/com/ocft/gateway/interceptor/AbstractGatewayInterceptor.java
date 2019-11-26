package com.ocft.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.common.exceptions.ReturnException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author lijiaxing
 * @Title: AbstractGatewayInterceptor
 * @ProjectName gateway
 * @date 2019/11/26下午2:02
 * @Description: 抽象网关拦截器类
 */
@Slf4j
@Component
public abstract class AbstractGatewayInterceptor implements GatewayInterceptor {
    @Override
    public abstract void doInterceptor(GatewayContext context);

    protected void returnResult(@Nullable Object data) {
//        HttpServletResponse response = context.getResponse();
//
//        //response.setContentType("application/json; charset=utf-8");
//        PrintWriter writer = null;
//        try {
//            writer = response.getWriter();
//            ServletOutputStream outputStream = response.getOutputStream();
//            outputStream.print(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue,
//                    SerializerFeature.WriteDateUseDateFormat));
//            outputStream.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        writer.print(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue,
////                SerializerFeature.WriteDateUseDateFormat));
//        try {
//            response.flushBuffer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        throw new ReturnException(data);
    }
}
