package com.ocft.gateway.web.dto.request;

import com.ocft.gateway.web.dto.PageDto;
import lombok.Data;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/4 11:36
 * @Description:
 */
@Data
public class QueryGatewayCacheRequest extends PageDto {

    private String id;
    private String name;
    private String url;
    private String backonUrl;
    private String status;
    private String resultNum;
    private String expireTime;
}
