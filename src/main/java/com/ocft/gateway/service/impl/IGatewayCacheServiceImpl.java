package com.ocft.gateway.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.GatewayCacheMapper;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.web.dto.request.QueryGatewayCacheRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/27 14:30
 * @Description:
 */
@Service
public class IGatewayCacheServiceImpl extends ServiceImpl<GatewayCacheMapper, GatewayCache> implements IGatewayCacheService {

    @Resource
    private GatewayCacheMapper gatewayCacheMapper;

    @Override
    public GatewayCache getGatewayCache(String uri) {
        GatewayCache gatewayCache = this.getOne(new QueryWrapper<GatewayCache>().eq("url", uri));
        Assert.notNull(gatewayCache,ResponseEnum.GATEWAY_CACHE_NOT_EXIST.getMessage());
        return gatewayCache;
    }

    @Override
    public IPage<GatewayCache> getGatewayCacheList(QueryGatewayCacheRequest request) {
//        IPage<GatewayCache> page = new Page<>(request.getCurrent(), request.getSize());
//        List<GatewayCache> gatewayCacheList = gatewayCacheMapper.getGatewayCacheList(request);
//        return page.setRecords(gatewayCacheList);
        QueryWrapper<GatewayCache> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getName())){
            queryWrapper.eq("name",request.getName());
        }
        if(StringUtils.isNotEmpty(request.getUrl())){
            queryWrapper.eq("url",request.getUrl());
        }
        if(StringUtils.isNotEmpty(request.getBackonUrl())){
            queryWrapper.eq("backon_url",request.getBackonUrl());
        }
        if(StringUtils.isNotEmpty(request.getStatus())){
            queryWrapper.eq("status",request.getStatus());
        }
        if(StringUtils.isNotEmpty(request.getResultNum())){
            queryWrapper.eq("result_num",request.getResultNum());
        }
        if(StringUtils.isNotEmpty(request.getExpireTime())){
            queryWrapper.eq("expire_time",request.getExpireTime());
        }
        IPage<GatewayCache> page = this.page(new Page<>(request.getCurrent(), request.getSize()),queryWrapper);
        return page;
    }
}
