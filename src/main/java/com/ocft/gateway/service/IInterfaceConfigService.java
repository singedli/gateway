package com.ocft.gateway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.InterfaceConfig;
import com.ocft.gateway.web.dto.request.QueryInterfaceRequest;

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

    IPage<InterfaceConfig> getPage(Integer pageNum, Integer pageSize);


    void modifyById(InterfaceConfig interfaceConfig);

    IPage<InterfaceConfig> queryInterfaceConfigs(QueryInterfaceRequest queryInterfaceRequest);
}
