package com.ocft.gateway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.web.dto.request.QueryGatewayCacheRequest;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/27 14:27
 * @Description:
 */
public interface IGatewayCacheService extends IService<GatewayCache> {
    GatewayCache getGatewayCache(String uri);

    IPage<GatewayCache> getGatewayCacheList(QueryGatewayCacheRequest request);
}
