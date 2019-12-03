package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.InterfaceConfig;
import com.ocft.gateway.mapper.InterfaceConfigMapper;
import com.ocft.gateway.service.IInterfaceConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Bobby
 * @since 2019-11-28
 */
@Service
public class InterfaceConfigServiceImpl extends ServiceImpl<InterfaceConfigMapper, InterfaceConfig> implements IInterfaceConfigService {

    @Override
    public InterfaceConfig getByUrl(String url) {
        InterfaceConfig interfaceConfig = this.getOne(new QueryWrapper<InterfaceConfig>().eq("url", url).eq("status", "1"));
        return interfaceConfig;
    }

    /**
     * 分页查询接口配置
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<InterfaceConfig> getPage(Integer pageNum, Integer pageSize) {
        IPage<InterfaceConfig> page = new Page<>(pageNum, pageSize);
        IPage<InterfaceConfig> page1 = this.page(page);
        return page1;
    }

    /**
     * 根据条件查询
     *
     * @param interfaceConfig
     * @return
     */
    @Override
    public IPage<InterfaceConfig> findByCondition(InterfaceConfig interfaceConfig, Integer pageNum, Integer pageSize) {
        IPage<InterfaceConfig> page = new Page<>(pageNum, pageSize);
        QueryWrapper<InterfaceConfig> eq = new QueryWrapper<InterfaceConfig>().eq("1", "1");
        if (StringUtils.isNotBlank(interfaceConfig.getKeyLimit())) {
            eq.eq("key_limit", interfaceConfig.getKeyLimit());
        }
        if (null != interfaceConfig.getMaxCount()) {
            eq.eq("max_count", interfaceConfig.getMaxCount());
        }
        if (StringUtils.isNotBlank(interfaceConfig.getUrl())) {
            eq.eq("url", interfaceConfig.getUrl());
        }
        if (StringUtils.isNotBlank(interfaceConfig.getUrl())) {
            eq.eq("url", interfaceConfig.getUrl());
        }
        if (null != interfaceConfig.getStatus()) {
            eq.eq("status", interfaceConfig.getStatus());
        }
        return this.page(page, eq);
    }

}
