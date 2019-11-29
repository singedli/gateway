package com.ocft.gateway.interceptor.pre;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.converter.GatewayContextConverter;
import com.ocft.gateway.common.evaluator.JsonOperateEvalutor;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.interceptor.AbstractGatewayInterceptor;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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

        if (redisUtil.existsHash(context.getGatewayInterface().getUrl(), field)) {
            //before
            //查询缓存不为空则返回缓存内容
            try {
                String result = (String) redisUtil.hget(context.getGatewayInterface().getUrl(), field);
                if (result != null) {
                    returnResult(result);
                }
            } catch (Exception e) {
                throw new GatewayException(ResponseEnum.REDIS_EXCEPTION);
            }
        }else if ( StringUtils.isNotEmpty(context.getCacheData()) ) {
            String responseString = context.getCacheData();
            System.out.println(responseString);

            //只缓存设置的字段
            JSONObject results = JSONObject.parseObject(responseString);
            JSONObject cacheData = null;
            if(context.getGatewayCache().getBackonUrl().split(",").length > 1){
                Set<String> keySet = results.keySet();
                for (String key:keySet) {
                    JSONObject datas = results.getJSONObject(key);
                    JsonOperateEvalutor.retain(datas, context.getGatewayCache().getResponseBody());

                    //缓存数据的条数为设置的数量
//                    List<Object> values = new ArrayList<>(datas.values());
//                    if(values.size()> context.getGatewayCache().getResultNum()){
//                        List<Object> subList = values.subList(0, context.getGatewayCache().getResultNum());
//                        System.out.println(subList);
//                    }
                    cacheData.put(key,datas);
                }
            }else {
                JsonOperateEvalutor.retain(results, context.getGatewayCache().getResponseBody());

                //缓存数据的条数为设置的数量
//                List<Object> values = new ArrayList<>(results.values());
//                if(values.size()> context.getGatewayCache().getResultNum())
//                    values = values.subList(0, context.getGatewayCache().getResultNum());
//                System.out.println(values);
                cacheData = results;
            }

            responseString = JSONObject.toJSONString(cacheData);

            try {
                redisUtil.hset(context.getGatewayInterface().getUrl(), field, responseString, context.getGatewayCache().getExpireTime() / 60);
            } catch (Exception e) {
                throw new GatewayException(ResponseEnum.REDIS_EXCEPTION);
            }
        }
    }
}