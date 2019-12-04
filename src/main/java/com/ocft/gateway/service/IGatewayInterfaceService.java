package com.ocft.gateway.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.web.dto.request.QueryGatewayInterfaceRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
public interface IGatewayInterfaceService extends IService<GatewayInterface> {
    GatewayInterface getGateWayInterface(String url);
    Page<GatewayInterface> queryGateWayInterfaces(QueryGatewayInterfaceRequest request);
}