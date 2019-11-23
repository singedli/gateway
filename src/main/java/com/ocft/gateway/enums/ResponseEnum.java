package com.ocft.gateway.enums;

import lombok.Getter;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/22 17:25
 * @Description:
 */
@Getter
public enum ResponseEnum {
    SUCCESS("00000000","成功"),
    FAIL("00000001","服务器异常"),
    BACKON_NOT_EXIST("10000000","接口对应的后台系统不存在"),


    HTTP_METHOD_NOT_EXIST_SUPPORTED("20000000","未知的HTTP请求方法");
    

    private String code;
    private String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
