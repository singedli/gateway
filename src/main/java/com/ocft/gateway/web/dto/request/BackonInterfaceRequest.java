package com.ocft.gateway.web.dto.request;

import com.ocft.gateway.web.dto.PageDto;
import lombok.Data;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/9 15:23
 * @Description:
 */
@Data
public class BackonInterfaceRequest extends PageDto {
    private String id;
    private String name;
    private String url;
    private String system;
    private String status;
    private String httpMethod;
}
