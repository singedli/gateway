package com.ocft.gateway.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.mapper.GatewayInterfaceMapper;
import com.ocft.gateway.service.IGatewayInterfaceService;
import org.springframework.stereotype.Service;

@Service
public class GatewayInterfaceServiceImpl extends ServiceImpl<GatewayInterfaceMapper, GatewayInterface> implements IGatewayInterfaceService {

}