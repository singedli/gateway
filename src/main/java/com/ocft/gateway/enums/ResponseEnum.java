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
    GATEWAY_INTERFACE_NOT_EXIST("10000001","gateway网关接口不存在"),
    BACKON_INTERFACE_NOT_EXIST("10000002","后台接口不存在"),
    REDIS_EXCEPTION("10000003","redis异常"),
    GATEWAY_CACHE_NOT_EXIST("10000004","gateway网关缓存配置不存在"),
    BACKON_INTERFACE_NOT_UNIQUE("10000005","网关接口对应的后台接口存在多个"),
    GATEWAY_CACHE_REFRESH_FAIL("10000006","gateway网关缓存刷新失败"),
    MESSAGE_CONVERTER_NOT_EXIST("10000007","gateway网关报文转换配置不存在"),
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
