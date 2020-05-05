/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.example.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回查询结果集
 *
 * @author maoyz on 18-11-13
 * @version: v1.0
 */
@Data
@ToString
public class QueryResult<T> implements Serializable {

    private static final long serialVersionUID = -6539447684692317035L;

    private Integer code;

    private String message;

    private T data;

    private Integer page;

    public static <T> QueryResult<T> success() {
        QueryResult<T> result = new QueryResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMsg());
        result.setData(null);
        return result;
    }

    public static <T> QueryResult<T> success(T data) {
        QueryResult<T> result = new QueryResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

}
