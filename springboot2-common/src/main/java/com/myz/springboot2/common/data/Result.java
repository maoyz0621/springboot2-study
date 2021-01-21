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

    public Result() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T t) {
        return success("SUCCESS", null);
    }

    public static <T> Result<T> success(String message, T t) {
        Result<T> result = new Result<>();
        result.setCode(ResultStatusEnum.SUCCESS.getStatus());
        result.setMessage(message);
        result.setData(t);
        return result;
    }

    public static <T> Result<T> fail() {
        return fail(ResultStatusEnum.FAIL.getDescribe());
    }

    public static <T> Result<T> fail(String msg) {
        return fail(ResultStatusEnum.FAIL.getStatus(), msg);
    }

    public static <T> Result<T> fail(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> fail(ResultStatusEnum resultStatusEnum) {
        return fail(resultStatusEnum.getStatus(), resultStatusEnum.getDescribe());
    }

    public boolean isSuccess() {
        return this.code == ResultStatusEnum.SUCCESS.getStatus();
    }

    public boolean isOk() {
        return this.code == ResultStatusEnum.SUCCESS.getStatus() && this.data != null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("data=").append(data);
        sb.append(", code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}