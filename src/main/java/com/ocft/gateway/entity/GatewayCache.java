package com.ocft.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/27 13:50
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GatewayCache {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String url;

    /**
     * 该接口需要调用的后台系统接口,并发和复杂类型用jsonArray的存储
     */
    private String backonUrl;

    /**
     * 接口请求参数key配置，以配置的key作为缓存的field
     */
    private String requestBody;

    /**
     * 接口响应字段配置 ，缓存只存接口返回数据中配置的数据
     */
    private String responseBody;

    /**
     * 缓存开关:1 表示启用,0 表示停用
     */
    private Boolean status;

    /**
     * 缓存条数：默认为20条以内
     */
    private Integer resultNum;

    /**
     * 缓存过期时间（单位分钟）：默认为30分钟
     */
    private Integer expireTime;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;
}
