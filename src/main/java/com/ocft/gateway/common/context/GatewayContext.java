package com.ocft.gateway.common.context;

import com.ocft.gateway.entity.GatewayInterface;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.ConstructorArgs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijiaxing
 * @Title: GatewayContext
 * @ProjectName gateway
 * @date 2019/11/23下午11:54
 * @Description: TODO
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
}
