package com.ocft.gateway.controller;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.enums.ResponseEnum;
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
    public ResponseEntity<?> handleException(HttpServletRequest request, GatewayException e){
        log.error(e.getMessage(),e);
        Map<String,Object> result = new HashMap<>();
        result.put("code",e.getCode());
        result.put("msg",e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleRuntimeException(HttpServletRequest request, Exception e){
        log.error(e.getMessage(),e);
        Map<String,Object> result = new HashMap<>();
        result.put("code",ResponseEnum.FAIL.getCode());
        result.put("msg",ResponseEnum.FAIL.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
