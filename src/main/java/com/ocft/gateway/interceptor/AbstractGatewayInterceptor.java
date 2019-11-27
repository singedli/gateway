package com.ocft.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.common.exceptions.ReturnException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



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
        throw new ReturnException(data);
    }
}
