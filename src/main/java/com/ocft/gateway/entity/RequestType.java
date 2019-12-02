package com.ocft.gateway.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bobby
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RequestType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 请求来源，浏览器或者app
     */
    private String type;

    /**
     * request请求头中的参数
     */
    private String agent;


}
