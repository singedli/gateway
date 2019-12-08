package com.ocft.gateway.web.dto.request;

import com.ocft.gateway.web.dto.PageDto;
import lombok.Data;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/8 16:58
 * @Description:
 */
@Data
public class MessageConverterRequest extends PageDto {

    private String id;

    private String name;

    private String url;

    private String backonUrl;

    private String status;
}
