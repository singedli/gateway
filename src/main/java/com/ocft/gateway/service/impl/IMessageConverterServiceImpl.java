package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.MessageConverter;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.MessageConverterMapper;
import com.ocft.gateway.service.IMessageConverterService;
import com.ocft.gateway.web.dto.request.MessageConverterRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/6 16:29
 * @Description:
 */
@Service
public class IMessageConverterServiceImpl extends ServiceImpl<MessageConverterMapper, MessageConverter> implements IMessageConverterService {
    @Override
    public MessageConverter getMessageConverter(String uri) {
        MessageConverter gatewayCache = this.getOne(new QueryWrapper<MessageConverter>().eq("url", uri));
        Assert.notNull(gatewayCache,ResponseEnum.MESSAGE_CONVERTER_NOT_EXIST.getMessage());
        return gatewayCache;
    }

    @Override
    public IPage<MessageConverter> getMessageConverterList(MessageConverterRequest request) {
        QueryWrapper<MessageConverter> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getName())){
            queryWrapper.eq("name",request.getName());
        }
        if(StringUtils.isNotEmpty(request.getUrl())){
            queryWrapper.eq("url",request.getUrl());
        }
        if(StringUtils.isNotEmpty(request.getBackonUrl())){
            queryWrapper.eq("backon_url",request.getBackonUrl());
        }
        if(StringUtils.isNotEmpty(request.getStatus())){
            queryWrapper.eq("status",request.getStatus());
        }
        IPage<MessageConverter> page = this.page(new Page<>(request.getCurrent(), request.getSize()),queryWrapper);
        return page;
    }
}
