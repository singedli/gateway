package com.ocft.gateway.service;

import com.ocft.gateway.entity.InterfaceConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bobby
 * @since 2019-11-28
 */
public interface IInterfaceConfigService extends IService<InterfaceConfig> {

    InterfaceConfig getByUrl(String url);
}
