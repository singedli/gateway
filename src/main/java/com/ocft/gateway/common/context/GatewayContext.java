package com.ocft.gateway.common.context;

import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.entity.GatewayInterface;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijiaxing
 * @Title: GatewayContext
 * @ProjectName gateway
 * @date 2019/11/23下午11:54
 * @Description: 网关上下文
 */
@Data
@Accessors(chain = true)
public class GatewayContext {
    private String requestBody;
    private HttpServletRequest request;
    private HttpServletResponse response;
    /**
     * 网关API实体类
     */
    private GatewayInterface gatewayInterface;
    /**
     * 网关cache实体类
     */
    private GatewayCache gatewayCache;
    /**
     * 后台实体类
     */
    private Backon backon;
    /**
     * 后台接口实体类
     */
    private BackonInterface backonInterface;
    /**
     * 响应数据
     */
    private String responseBody;

}
