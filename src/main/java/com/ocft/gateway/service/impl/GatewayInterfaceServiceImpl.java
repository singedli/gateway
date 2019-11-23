package com.ocft.gateway.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.GatewayInterfaceMapper;
import com.ocft.gateway.service.IGatewayInterfaceService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GatewayInterfaceServiceImpl extends ServiceImpl<GatewayInterfaceMapper, GatewayInterface> implements IGatewayInterfaceService {

    @Override
    public GatewayInterface getGateWayInterface(String url) {
        GatewayInterface gatewayInterface = this.getOne(new QueryWrapper<GatewayInterface>().eq("url", url).eq("status", "1"));
        Assert.notNull(gatewayInterface,ResponseEnum.GATEWAY_INTERFACE_NOT_EXIST.getMessage());
        return gatewayInterface;
    }
}