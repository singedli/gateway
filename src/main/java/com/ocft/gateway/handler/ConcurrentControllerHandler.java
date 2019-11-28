package com.ocft.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.converter.GatewayContextConverter;
import com.ocft.gateway.common.task.ConcurrentInvokeTask;
import com.ocft.gateway.entity.GatewayInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author lijiaxing
 * @Title: ConcurrentControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:21
 * @Description: 并发类型的处理器
 */
@Component
public class ConcurrentControllerHandler extends AbstractControllerHandler {
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public String buildReqHeader() {
        return null;
    }

    @Override
    public String buildReqBody(String body) {
        return null;
    }

    @Override
    public String sendToBacon(GatewayContext gatewayContext) {
        GatewayInterface gatewayInterface = gatewayContext.getGatewayInterface();
        int count = gatewayInterface.getBackonUrl().split(",").length;
        CountDownLatch latch = new CountDownLatch(count);
        List<ConcurrentInvokeTask> tasks = GatewayContextConverter.convert(gatewayContext, latch);
        for (ConcurrentInvokeTask task : tasks) {
            executor.execute(task);
        }
        try {
            //阻塞主线程，直到所有任务执行完成
            latch.await();
        } catch (InterruptedException e) {
            //中断异常
        }
        JSONObject result = new JSONObject();
        for (ConcurrentInvokeTask task : tasks) {
            result.put(task.getUrl(),task.getRes());
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {
        return null;
    }
}
