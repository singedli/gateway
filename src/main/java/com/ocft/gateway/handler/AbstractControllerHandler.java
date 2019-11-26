package com.ocft.gateway.handler;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.converter.GatewayContextConverter;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.service.IBackonService;
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
    public abstract String sendToBacon(String header, String body,GatewayInterface gatewayInterface);

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
        String responseString = null;

        //缓存数据不为空则返回缓存内容
        if(!StringUtils.isEmpty(gatewayContext.getCacheData())){
            responseString = gatewayContext.getCacheData();
        }else {
            //responseString = sendToBacon(requestHeader, requestBody, gatewayContext.getGatewayInterface());
            responseString = "CacheDataTest";//缓存测试

            //取field，并且设置缓存
            String field = GatewayContextConverter.convertRedisHashField(gatewayContext);
            try {
                redisUtil.hset(gatewayContext.getGatewayInterface().getUrl(), field , responseString);
            }catch (Exception e){
                throw new GatewayException(ResponseEnum.REDIS_EXCEPTION);
            }
        }
        return retToClient(responseString, gatewayContext.getRequest());
    }

    public Map<String, Object> handle(List<GatewayContext> gatewayContexts) throws Exception{
        for (GatewayContext gatewayContext : gatewayContexts) {
            //String requestHeader = this.buildReqHeader();
        }
        return null;
    }
}
