package com.ocft.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.GatewayCache;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/27 14:27
 * @Description:
 */
public interface IGatewayCacheService extends IService<GatewayCache> {
    GatewayCache getGatewayCache(String uri);
}
