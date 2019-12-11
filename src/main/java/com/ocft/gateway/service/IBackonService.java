package com.ocft.gateway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.web.dto.request.BackonRequest;

import java.util.List;

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


    IPage<Backon> queryBackons(BackonRequest request);

    void deleteByIds(List<String> strings);

    void modifyById(Backon backon);
}