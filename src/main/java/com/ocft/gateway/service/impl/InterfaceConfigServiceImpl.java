package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
}
