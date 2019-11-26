package com.ocft.gateway.common.exceptions;

import lombok.Data;

/**
 * @author lijiaxing
 * @Title: ReturnException
 * @ProjectName gateway
 * @date 2019/11/26下午4:10
 * @Description: TODO
 */
@Data
public class ReturnException extends RuntimeException {
    private Object data;
    public ReturnException(Object data){
        this.data = data;
    }
}
