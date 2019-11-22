package com.ocft.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("backon")
public class Backon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统标识
     */
    private String system;

    /**
     * 后台系统的域名或ip
     */
    private String domain;

    /**
     * 后缀,系统接口的后缀如 .htm 或.do等
     */
    private String suffix;

    /**
     * 状态信息:1表示有效，0表示失效
     */
    private Integer status;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 返回码键
     */
    private String successCode;

    /**
     * 表成功的返回值
     */
    private String successValue;

    /**
     * 逻辑删除标志位，1表示已删除，0表示未删除
     */
    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;


}
