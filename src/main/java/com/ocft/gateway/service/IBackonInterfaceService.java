package com.ocft.gateway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.web.dto.request.BackonInterfaceRequest;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
public interface IBackonInterfaceService extends IService<BackonInterface> {
    BackonInterface getBackonInterface(String url);
    String getBackonInterfaceMethod(String url);
    Map<String,String> getBackonInterfaceMethod(GatewayInterface gatewayInterface);

    IPage<BackonInterface> getBackonInterfaceList(BackonInterfaceRequest request);
    List<BackonInterface> getBackonInterfaceListBySystem(BackonInterfaceRequest request);
    BackonInterface getBackonInterfaceListByUrl(BackonInterfaceRequest request);
}