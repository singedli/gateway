package com.ocft.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
public interface IBackonInterfaceService extends IService<BackonInterface> {
    BackonInterface getBackonInterface(String url);
    String getBackonInterfaceMethod(String url);
    Map<String,String> getBackonInterfaceMethod(GatewayInterface gatewayInterface);
}