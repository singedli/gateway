package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.service.IBackonService;
import com.ocft.gateway.web.dto.request.BackonRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author lijiaxing
 * @since 2019-11-22
 */
@Service
public class BackonServiceImpl extends ServiceImpl<BackonMapper, Backon> implements IBackonService {


    @Autowired
    BackonMapper backonMapper;

    @Override
    public Backon getBackon(String system) {
        Backon backon = this.getOne(new QueryWrapper<Backon>().eq("system", system).eq("status", "1").eq("is_deleted", "0"));
        Assert.notNull(backon, ResponseEnum.BACKON_NOT_EXIST.getMessage());
        return backon;
    }


    //根据条件分页查询
    @Override
    public IPage<Backon> queryBackons(BackonRequest request) {
        Page<Backon> page = new Page<>(request.getCurrent(), request.getSize());
        List<Backon> gatewayInterfaces = backonMapper.queryBackons(page, request);
        return page.setRecords(gatewayInterfaces);
    }

    /**
     * 根据id逻辑删除数据
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        backonMapper.deleteByIds(ids);
    }

    /**
     * 根据id修改数据
     * @param backon
     */
    @Override
    public void modifyById(Backon backon) {
        Backon newBackon = new Backon();
        BeanUtils.copyProperties(backon, newBackon);
        this.updateById(newBackon);
    }
}
