/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.example.common;

/**
 * @author maoyz on 18-11-13
 * @version: v1.0
 */
public enum ResultCodeEnum {
    SUCCESS(200, "SUCCESS"),//成功
    FAIL(400, ""),//失败
    UNAUTHORIZED(401, ""),//未认证（签名错误）
    TYPE_ERROR(9999, ""),
    NOT_FOUND(404, ""),//接口不存在
    INTERNAL_SERVER_ERROR(500, "");//服务器内部错误

    private final int code;
    private final String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return msg;
    }}
