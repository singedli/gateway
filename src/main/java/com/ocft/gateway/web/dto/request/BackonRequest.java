package com.ocft.gateway.web.dto.request;

import com.ocft.gateway.web.dto.PageDto;
import lombok.Data;

/**
 * @author: Bobby
 * @create: 2019-12-10 09:49
 * @description: 后台系统请求参数
 **/
@Data
public class BackonRequest  extends PageDto {
    private String id;
    private String system;
    private String domain;
    private String suffix;
    private String status;
    private String description;
    private String success_code;
    private String success_value;

}
