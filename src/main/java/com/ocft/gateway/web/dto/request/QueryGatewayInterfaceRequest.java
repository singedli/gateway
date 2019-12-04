package com.ocft.gateway.web.dto.request;

import com.ocft.gateway.web.dto.PageDto;
import lombok.Data;

/**
 * @author lijiaxing
 * @Title: QueryGatewayInterfaceRequest
 * @ProjectName gateway
 * @date 2019/12/4上午9:51
 * @Description: TODO
 */
@Data
public class QueryGatewayInterfaceRequest extends PageDto {
    private String name;
    private String url;
    private String backonUrl;
    private String status;
    private String type;
}
