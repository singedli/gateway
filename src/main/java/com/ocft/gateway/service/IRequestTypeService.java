package com.ocft.gateway.service;

import com.ocft.gateway.entity.RequestType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bobby
 * @since 2019-11-26
 */
public interface IRequestTypeService extends IService<RequestType> {

    List<RequestType> findTypeBrowser();

    List<RequestType> findTypeApp();

    List<RequestType> findAllRequestType();

    RequestType findById(String id);
}
