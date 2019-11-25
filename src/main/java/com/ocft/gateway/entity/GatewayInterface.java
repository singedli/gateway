package com.ocft.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GatewayInterface implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String url;

    /**
     * 1.表示透传接口 2.串行化组合接口 3.并行化组合接口 4.复杂逻辑组合接口
     */
    private String type;

    /**
     * 状态信息:1 表示启用,0 表示停用
     */
    private Boolean status;

//    /**
//     * 请求后台的方式get or post
//     */
//    private String httpMethod;

    private String backonUrl;

    /**
     * 该接口调用的系统,如涉及多个系统则用逗号隔开
     */
    private String system;

    private String version;
    /**
     * 接口调用前的拦截器的配置，多个拦截器使用逗号隔开,默认为空
     */
    private String preInterceptors;
    /**
     * 接口调用后的拦截器的配置，多个拦截器使用逗号隔开,默认为空
     */
    private String postInterceptors;

    /**
     * 逻辑删除标志位，1表示已删除，0表示未删除
     */
    private Integer isDeleted;

    private String invokeConfig;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;


}
