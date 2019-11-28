package com.ocft.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.Backon;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
public interface IBackonService extends IService<Backon> {
    Backon getBackon(String system);
}