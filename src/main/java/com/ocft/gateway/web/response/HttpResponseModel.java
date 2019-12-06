package com.ocft.gateway.web.response;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Bobby
 * @create: 2019-12-06 15:06
 * @description: 访问接口返回模板
 **/
@Data
public class HttpResponseModel<T> implements Serializable {
    private static final long serialVersionUID = -4516692141234738922L;

    //状态码
    private String code;
    //业务提示语
    private String msg;
    //数据对象
    private T data;

    public HttpResponseModel() {
        this.code = ErrorCode.SYSTEM_SUCCESS.getCode();
        this.msg = ErrorCode.SYSTEM_SUCCESS.getMsg();
        this.data = (T) new Object();
    }

    public HttpResponseModel(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = (T) new Object();
    }

    public HttpResponseModel(T data) {
        this.code = ErrorCode.SYSTEM_SUCCESS.getCode();
        this.msg = ErrorCode.SYSTEM_SUCCESS.getMsg();
        if(data != null){
            this.data = data;
        }else {
            this.data = (T) new Object();
        }
    }

    public HttpResponseModel(String code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        if(data != null){
            this.data = data;
        }else {
            this.data = (T) new Object();
        }
    }


    public String toString(){
        return JSONObject.toJSONString(this);
    }




}
