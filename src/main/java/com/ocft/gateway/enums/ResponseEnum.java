package com.ocft.gateway.enums;

import lombok.Getter;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/22 17:25
 * @Description:
 */
@Getter
public enum ResponseEnum {
    SUCCESS("200","成功"),
    FAIL("500","服务器异常")
    ;
    private String code;

    private String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
