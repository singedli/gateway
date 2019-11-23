package com.ocft.gateway.common.exceptions;

import com.ocft.gateway.enums.ResponseEnum;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/22 15:28
 * @Description:
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
