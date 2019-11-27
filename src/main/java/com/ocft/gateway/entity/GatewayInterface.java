package com.ocft.gateway.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GatewayInterface implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String url;

    /**
     * 该接口需要调用的后台系统接口,并发和复杂类型用jsonArray的存储
     */
    private String backonUrl;

    /**
     * 1.表示透传接口 2.串行化组合接口 3.并行化组合接口 4.复杂逻辑组合接口
     */
    private String type;

    /**
     * 状态信息:1 表示启用,0 表示停用
     */
    private Boolean status;

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;

    /**
     * 需要做限制的数据key用逗号分隔
     */
    @TableField("keyLimit")
    private String keyLimit;


}
