package com.ocft.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.GatewayInterface;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
public interface IGatewayInterfaceService extends IService<GatewayInterface> {
    GatewayInterface getGateWayInterface(String uri);
}