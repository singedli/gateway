package com.ocft.gateway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocft.gateway.entity.MessageConverter;
import com.ocft.gateway.web.dto.request.MessageConverterRequest;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/6 16:29
 * @Description:
 */
public interface IMessageConverterService extends IService<MessageConverter> {

    MessageConverter getMessageConverter(String uri);

    IPage<MessageConverter> getMessageConverterList(MessageConverterRequest request);
}
