package com.ocft.gateway.common.converter;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.task.ConcurrentInvokeTask;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.spring.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author lijiaxing
 * @Title: GatewayContextConverter
 * @ProjectName gateway
 * @date 2019/11/24下午3:40
 * @Description: TODO
 */
public class GatewayContextConverter {

    public static List<ConcurrentInvokeTask> convert(GatewayContext gatewayContext, CountDownLatch latch){
        List<ConcurrentInvokeTask> taskList = new ArrayList<>();
        GatewayInterface gatewayInterface = gatewayContext.getGatewayInterface();
        String backonUrls = gatewayInterface.getBackonUrl();
        for (String backonUrl : backonUrls.split(",")) {
            IBackonInterfaceService backonInterfaceService = SpringContextHolder.getBean(IBackonInterfaceService.class);
            BackonInterface backonInterface = backonInterfaceService.getBackonInterface(backonUrl);
            ConcurrentInvokeTask concurrentInvokeTask = new ConcurrentInvokeTask()
                    .setHeader(backonInterface.getHttpHeader())
                    .setHttpMethod(backonInterface.getHttpMethod())
                    .setLatch(latch)
                    .setRequestBody(gatewayContext.getRequestBody());
            taskList.add(concurrentInvokeTask);
        }
        Assert.notEmpty(taskList,ResponseEnum.BACKON_INTERFACE_NOT_EXIST.getMessage());
        return taskList;
    }
}
