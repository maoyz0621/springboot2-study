/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.example.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回操作结果集
 *
 * @author maoyz on 18-11-13
 * @version: v1.0
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -6539447684692317035L;

    private ResultCodeEnum code;
    private String message;
    private T data;

    public ResultCodeEnum getCode() {
        return code;
    }

    public Result() {
    }

    public Result setCode(ResultCodeEnum resultCode) {
        this.code = resultCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }
}
