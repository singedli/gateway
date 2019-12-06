package com.ocft.gateway.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author: Bobby
 * @create: 2019-12-06 15:26
 * @description: 调用外部接口实体类
 **/
@Data
public class InvokeThirdDTO {

    //请求方法
    private String method;

    //请求路径
    private String backOnUrl;


    //请求参数
    private String requestData;

    //请求参数
    private String formParams;

    //请求头信息
    private Map<String, String> header;

    //请求是否加密
    private String reqencrypt;

    //返回数据是否加密
    private String respdecrypt;

    //第三方响应数据
    private String data;

    //第三方系统响应信息
    private String msg;

    //第三方系统响应码
    private String code;


    //第三方系统响应成功码
    private String success;

    private MultipartFile file;

}
