package com.ocft.gateway.exception;

import com.ocft.gateway.enums.ResponseEnum;
import lombok.Data;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/22 15:28
 * @Description:
 */
@Data
public class GwException extends RuntimeException {

    private String code;

    public GwException() {}

    public GwException(String code) {
        this.code = code;
    }

    public GwException(String code, String message) {
        //传到父类的message里
        super(message);

        this.code = code;
    }

    public GwException(String code, String message,Throwable throwable) {
        //传到父类的message里
        super(message,throwable);

        this.code = code;
    }

    public GwException(ResponseEnum responseEnum) {
        this(responseEnum.getCode(),responseEnum.getMessage());
    }
}
