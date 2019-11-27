package com.ocft.gateway.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.GatewayCacheMapper;
import com.ocft.gateway.service.IGatewayCacheService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/27 14:30
 * @Description:
 */
@Service
public class IGatewayCacheServiceImpl extends ServiceImpl<GatewayCacheMapper, GatewayCache> implements IGatewayCacheService {
    @Override
    public GatewayCache getGatewayCache(String uri) {
        GatewayCache gatewayCache = this.getOne(new QueryWrapper<GatewayCache>().eq("url", uri).eq("status", "1"));
        Assert.notNull(gatewayCache,ResponseEnum.GATEWAY_CACHE_NOT_EXIST.getMessage());
        return gatewayCache;
    }
}
