package com.ocft.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("backon_interface")
public class BackonInterface implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    /**
     * 接口所属的系统，对应于backon表的system字段
     */
    private String system;

    private String url;

    /**
     * 状态信息:1表示有效，0表示失效
     */
    private Integer status;

    /**
     * get or post
     */
    private String httpMethod;

    private String version;

    /**
     * 逻辑删除标志位，1表示已删除，0表示未删除
     */
    private Integer isDeleted;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;


}
