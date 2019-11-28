package com.ocft.gateway.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.converter.GatewayContextConverter;
import com.ocft.gateway.common.evaluator.JsonSlimEvalutor;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.service.IBackonService;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: AbstractControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:18
 * @Description: 处理器控制器抽象类
 */
@Component
public abstract class AbstractControllerHandler implements IControllerHandler {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    protected IBackonService backonService;

    @Autowired
    protected IBackonInterfaceService backonInterfaceService;

    @Autowired
    protected IGatewayInterfaceService gatewayInterfaceService;

    @Autowired
    private IGatewayCacheService gatewayCacheService;
    /**
     * 构造请求报文头
     *
     * @return
     */
    public abstract String buildReqHeader();

    /**
     * 构造请求报文体
     *
     * @param body
     * @return
     */
    public abstract String buildReqBody(String body);

    /**
     * 发送报文给后台
     *
     * @param header
     * @param body
     * @return
     */
    public abstract String sendToBacon(GatewayContext gatewayContext);

    /**
     * 返回前端处理
     *
     * @param resout
     * @return
     */
    public abstract Map<String, Object> retToClient(String resout, HttpServletRequest request);


    @Override
    public Map<String, Object> handle(GatewayContext gatewayContext) throws Exception {
        String requestHeader = this.buildReqHeader();
        String requestBody = this.buildReqBody(gatewayContext.getRequestBody());

        String responseString = sendToBacon(gatewayContext);
//        String responseString = "CacheDataTest";//缓存测试
//
//        //检查接口缓存状态是否为开启
//        if(gatewayCacheService.getGatewayCache("global").getStatus()||gatewayContext.getGatewayCache().getStatus()){
//            //只缓存设置的字段
//            JSONArray results = JsonSlimEvalutor.retain(JSONObject.parseArray(responseString), gatewayContext.getGatewayCache().getResponseBody());
//            //缓存数据的条数为设置的数量
//            if (results.size() > gatewayContext.getGatewayCache().getResultNum()){
//                results = (JSONArray) results.subList(0,gatewayContext.getGatewayCache().getResultNum());
//            }
//            responseString = JSONObject.toJSONString(results);
//
//            //取field，并且设置缓存
//            String field = GatewayContextConverter.convertRedisHashField(gatewayContext);
//            try {
//                if (redisUtil.hget(gatewayContext.getGatewayInterface().getUrl(), field) == null)
//                    redisUtil.hset(gatewayContext.getGatewayInterface().getUrl(), field , responseString, gatewayContext.getGatewayCache().getExpireTime()/60 );
//            }catch (Exception e){
//                throw new GatewayException(ResponseEnum.REDIS_EXCEPTION);
//            }
//        }

        return retToClient(responseString, gatewayContext.getRequest());
    }

    public Map<String, Object> handle(List<GatewayContext> gatewayContexts) throws Exception{
        for (GatewayContext gatewayContext : gatewayContexts) {
            //String requestHeader = this.buildReqHeader();
        }
        return null;
    }
}
