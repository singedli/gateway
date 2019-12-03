package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.InterfaceConfig;
import com.ocft.gateway.mapper.InterfaceConfigMapper;
import com.ocft.gateway.service.IInterfaceConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
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
}
