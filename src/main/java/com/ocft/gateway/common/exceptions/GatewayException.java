package com.ocft.gateway.common.exceptions;

import com.ocft.gateway.enums.ResponseEnum;

/**
 * @author lijiaxing
 * @Title: GatewayException
 * @ProjectName gateway
 * @date 2019/11/24上午1:15
 * @Description: 自定义异常
 */
public class GatewayException extends RuntimeException {
    private String code;

    public GatewayException() {}

    public GatewayException(String code) {
        this.code = code;
    }

    public GatewayException(String code, String message) {
        //传到父类的message里
        super(message);
        this.code = code;
    }

    public GatewayException(String code, String message, Throwable throwable) {
        //传到父类的message里
        super(message,throwable);
        this.code = code;
    }

    public GatewayException(ResponseEnum responseEnum) {
        this(responseEnum.getCode(),responseEnum.getMessage());
    }
}
