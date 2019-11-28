package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.service.IBackonService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
@Service
public class BackonServiceImpl extends ServiceImpl<BackonMapper, Backon> implements IBackonService {

    @Override
    public Backon getBackon(String system) {
        Backon backon = this.getOne(new QueryWrapper<Backon>().eq("system", system).eq("status", "1").eq("is_deleted", "0"));
        Assert.notNull(backon,ResponseEnum.BACKON_NOT_EXIST.getMessage());
        return backon;
    }
}
