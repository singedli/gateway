package com.ocft.gateway.controller;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.common.exceptions.ReturnException;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/22 17:13
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = GatewayException.class)
    public ResponseEntity<?> handleException(GatewayException e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(ResultUtil.bizExceptionResult(e), HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleRuntimeException(Exception e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(ResultUtil.exceptionResult(), HttpStatus.OK);
    }

    @ExceptionHandler(value = ReturnException.class)
    public ResponseEntity<?> handleReturnException(ReturnException e){
        return new ResponseEntity<>(ResultUtil.createResult(e.getData()), HttpStatus.OK);
    }
}
