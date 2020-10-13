/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-10-13 18:41  Inc. All rights reserved.
 */
package com.myz.springboot2.common.exception;

/**
 * @author maoyz
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 6764605309905153249L;
    private int status = 200;
    private String msg = null;
    private T data = null;

    public Result() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result();
        result.setStatus(ResultStatusEnum.SUCCESS.getStatus());
        result.setMsg("SUCCESS");
        return result;
    }

    public static <T> Result<T> success(T t) {
        Result<T> result = new Result();
        result.setStatus(ResultStatusEnum.SUCCESS.getStatus());
        result.setMsg("SUCCESS");
        result.setData(t);
        return result;
    }

    public static <T> Result<T> success(T t, String message) {
        Result<T> result = new Result();
        result.setStatus(ResultStatusEnum.SUCCESS.getStatus());
        result.setMsg(message);
        result.setData(t);
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result();
        result.setStatus(ResultStatusEnum.FAIL.getStatus());
        result.setMsg(ResultStatusEnum.FAIL.getDescribe());
        return result;
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> result = new Result();
        result.setStatus(ResultStatusEnum.FAIL.getStatus());
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> fail(int status, String msg) {
        Result<T> result = new Result();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> fail(ResultStatusEnum resultStatusEnum) {
        Result<T> result = new Result();
        result.setStatus(resultStatusEnum.getStatus());
        result.setMsg(resultStatusEnum.getDescribe());
        return result;
    }

    public boolean isSuccess() {
        return this.status == ResultStatusEnum.SUCCESS.getStatus();
    }

    public boolean isOk() {
        return this.status == ResultStatusEnum.SUCCESS.getStatus() && this.data != null;
    }

    public String toString() {
        return "Result{status=" + this.status + ", msg='" + this.msg + '\'' + ", data=" + this.data + '}';
    }
}