package com.ocft.gateway.web.dto.request;

import com.ocft.gateway.web.dto.PageDto;
import lombok.Data;

/**
 * @author: Bobby
 * @create: 2019-12-04 16:24
 * @description: 查询接口配置类
 **/
@Data
public class QueryInterfaceRequest extends PageDto {
    private String status;
    private String keyLimit;
    private String url;
    private String maxCount;
    private String timeUnit;
}
