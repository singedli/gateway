package com.ocft.gateway.entity;

import java.time.LocalDateTime;
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
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InterfaceConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口地址，对应gateway_interface中的url
     */
    private String url;

    /**
     * 该接口需要验证的参数的key
     */
    private String keyLimit;

    /**
     * 单位时间(默认秒)内最大的访问次数
     */
    private Integer maxCount;

    /**
     * 是否开启:1 表示启用,0 表示停用
     */
    private Boolean status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;


}
