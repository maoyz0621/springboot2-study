/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2error.error;

import com.myz.springboot2error.common.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author maoyz0621 on 20-4-21
 * @version v1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else if (e instanceof BindException) {
            return null;
        } else if (e instanceof BizException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return null;
    }
}