package com.ocft.gateway.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.GatewayInterfaceMapper;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.web.dto.request.QueryGatewayInterfaceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class GatewayInterfaceServiceImpl extends ServiceImpl<GatewayInterfaceMapper, GatewayInterface> implements IGatewayInterfaceService {

    @Autowired
    private GatewayInterfaceMapper gatewayInterfaceMapper;

    @Override
    public GatewayInterface getGateWayInterface(String url) {
        GatewayInterface gatewayInterface = this.getOne(new QueryWrapper<GatewayInterface>().eq("url", url).eq("status", "1").eq("is_deleted","0"));
        Assert.notNull(gatewayInterface,ResponseEnum.GATEWAY_INTERFACE_NOT_EXIST.getMessage());
        return gatewayInterface;
    }

    public Page<GatewayInterface> queryGateWayInterfaces(QueryGatewayInterfaceRequest request){
        Page<GatewayInterface> page = new Page<>(request.getCurrent(), request.getSize());
        List<GatewayInterface> gatewayInterfaces = gatewayInterfaceMapper.queryGateWayInterfaces(page, request);
        return page.setRecords(gatewayInterfaces);
    }
}