package com.ocft.gateway.controller;

import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.exception.GwException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/22 17:13
 * @Description:
 */

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = GwException.class)
    public ResponseEntity<?> hanlerException(HttpServletRequest request, GwException e){
        return new ResponseEntity<>(ResponseEnum.FAIL, HttpStatus.OK);
    }
}
