package com.ocft.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: Bobby
 * @create: 2019-11-25 17:57
 * @description: 防恶意请求限制
 **/

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RequestAccessLimit implements Serializable {
    private static final long serialVersionUID = 566840398906961311L;
    private long firstRequestTime;
    private long count;
    private Boolean needLogin;
}
