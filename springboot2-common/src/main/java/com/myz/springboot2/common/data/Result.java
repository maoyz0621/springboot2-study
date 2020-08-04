/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-28 18:07  Inc. All rights reserved.
 */
package com.myz.springboot2.common.data;

/**
 * @author maoyz
 */
public class Result<T> extends BaseResult {
    private static final long serialVersionUID = 1L;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> of(T data) {
        Result result = new Result();
        result.setCode(0);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static Result buildSuccess() {
        Result result = new Result();
        result.setCode(0);
        result.setMessage("success");
        return result;
    }

    public static Result buildFailure() {
        Result result = new Result();
        result.setCode(-1);
        result.setMessage("");
        return result;
    }

    public static Result buildFailure(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}