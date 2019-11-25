package com.ocft.gateway.common.converter;

import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.task.ConcurrentInvokeTask;
import com.ocft.gateway.entity.GatewayInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijiaxing
 * @Title: GatewayContextConverter
 * @ProjectName gateway
 * @date 2019/11/24下午3:40
 * @Description: TODO
 */
public class GatewayContextConverter {
    public static ConcurrentInvokeTask convert(GatewayContext gatewayContext){
        GatewayInterface gatewayInterface = gatewayContext.getGatewayInterface();
        String backonUrl = gatewayInterface.getBackonUrl();

        return null;
    }

//    public static List<ConcurrentInvokeTask> convert(List<GatewayContext> gatewayContexts){
//        List<ConcurrentInvokeTask> concurrentInvokeTasks = new ArrayList<>(gatewayContexts.size());
//        for (GatewayContext gatewayContext : gatewayContexts) {
//            concurrentInvokeTasks.add(convert(gatewayContext));
//        }
//        return concurrentInvokeTasks;
//    }
}
