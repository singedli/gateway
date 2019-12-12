package com.ocft.gateway.handler;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.entity.GatewayInterface;
import io.seata.saga.engine.StateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: ComplicatedControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:22
 * @Description: 复杂类型处理器
 */
@Component
public class ComplicatedControllerHandler extends AbstractControllerHandler {

    @Autowired
    private StateMachineEngine engine;

    @Override
    public String buildReqHeader() {
        return null;
    }

    @Override
    public String buildReqBody(String body) {
        return body;
    }

    @Override
    public String sendToBacon(GatewayContext gatewayContext) {
        //获取状态机引擎
        //获取状态机脚本
        //设置状态机参数
        //执行
        return null;
    }

    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return null;
    }
}
