package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.service.IBackonService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
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

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Backon> getPage(Integer pageNum, Integer pageSize) {
        QueryWrapper<Backon> eq = new QueryWrapper<Backon>().eq("status", "1").eq("isDeleted","0");
        IPage<Backon> page = new Page<>(pageNum, pageSize);
        IPage<Backon> page1 = this.page(page,eq);
        return page1;
    }
}
