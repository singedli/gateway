package com.ocft.gateway.interceptor.pre;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.converter.GatewayContextConverter;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.interceptor.AbstractGatewayInterceptor;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijiaxing
 * @Title: RequestCacheInterceptor
 * @ProjectName gateway
 * @date 2019/11/23下午6:17
 * @Description: 请求缓存拦截器
 */
@Slf4j
@Component
public class RequestCacheInterceptor extends AbstractGatewayInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IGatewayCacheService gatewayCacheService;

    @Override
    public void doInterceptor(GatewayContext context) {
        System.out.println("请求缓存拦截器被执行！");
        //检查缓存开关，如果缓存为关闭状态则退出方法执行handle方法
        if (!gatewayCacheService.getGatewayCache("global").getStatus()
                ||!context.getGatewayCache().getStatus()) return;

        //把请求body参数转换为“key1_vlaue1_key2_value2_...”的字符串
        String field = GatewayContextConverter.convertRedisHashField(context);

        //before
        //查询缓存不为空则返回缓存内容
        try {
            String result = (String) redisUtil.hget(context.getGatewayInterface().getUrl(), field);
            if (result != null) {
                returnResult(result);
            }
        }catch (Exception e){
            throw new GatewayException(ResponseEnum.REDIS_EXCEPTION);
        }
    }
}
