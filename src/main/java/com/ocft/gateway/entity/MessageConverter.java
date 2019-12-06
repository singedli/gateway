package com.ocft.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/6 16:24
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageConverter implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String url;

    /**
     * 该接口需要调用的后台系统接口,并发和复杂类型用jsonArray的存储
     */
    private String backonUrl;

    /**
     * 请求报文配置:A = a,B.C = b.c (=左边为网关请求的报文字段，右边为要转换成的报文字段)
     */
    private String requestConfig;

    /**
     * 响应报文配置:A = a,B.C = b.c (=左边为网关响应的报文字段，右边为要转换成的报文字段)
     */
    private String responseConfig;

    /**
     * 请求报文格式配置
     */
    private String requestStruct;

    /**
     * 响应报文格式配置
     */
    private String responseStruct;

    /**
     * 1 表示启用,0 表示停用
     */
    private Boolean status;

    /**
     * 逻辑删除标志位，1表示已删除，0表示未删除
     */
    private Boolean isDeleted;


    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;
}
