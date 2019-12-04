package com.ocft.gateway.web.dto;

import lombok.Data;

/**
 * @author lijiaxing
 * @Title: BaseDto
 * @ProjectName gateway
 * @date 2019/12/4上午9:52
 * @Description: TODO
 */
@Data
public class PageDto {
    private Integer current;
    private Integer size;
}
