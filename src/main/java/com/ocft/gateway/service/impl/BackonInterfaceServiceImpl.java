package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.BackonInterfaceMapper;
import com.ocft.gateway.service.IBackonInterfaceService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
@Service
public class BackonInterfaceServiceImpl extends ServiceImpl<BackonInterfaceMapper, BackonInterface> implements IBackonInterfaceService {

    @Override
    public BackonInterface getBackonInterface(String url) {
        List<BackonInterface> backonInterfaces = this.list(new QueryWrapper<BackonInterface>().eq("url", url).eq("status", "1").eq("is_deleted", "0"));
        BackonInterface backonInterface = this.getOne(new QueryWrapper<BackonInterface>().eq("url", url).eq("status", "1").eq("is_deleted", "0"));
        Assert.notNull(backonInterface,ResponseEnum.BACKON_INTERFACE_NOT_EXIST.getMessage());
        return backonInterface;
    }

    @Override
    public String getBackonInterfaceMethod(String url) {
        BackonInterface backonInterface = this.getBackonInterface(url);
        return backonInterface.getHttpMethod();
    }

    @Override
    public Map<String, String> getBackonInterfaceMethod(GatewayInterface gatewayInterface) {
        Map<String,String> urlMethodMap = new HashMap<>();
        String[] backonUrls = gatewayInterface.getBackonUrl().split(",");
        for (String backonUrl : backonUrls) {
            urlMethodMap.put(backonUrl,this.getBackonInterfaceMethod(backonUrl));
        }
        return urlMethodMap;
    }
}
