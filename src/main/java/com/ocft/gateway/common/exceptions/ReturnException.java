package com.ocft.gateway.common.exceptions;

import lombok.Data;

/**
 * @author lijiaxing
 * @Title: ReturnException
 * @ProjectName gateway
 * @date 2019/11/26下午4:10
 * @Description: 该异常会将当前线程中断，并将data
 */
@Data
public class ReturnException extends RuntimeException {
    private Object data;
    public ReturnException(Object data){
        this.data = data;
    }
}
