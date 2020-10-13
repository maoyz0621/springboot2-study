/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-10-13 18:41  Inc. All rights reserved.
 */
package com.myz.springboot2.common.data;

/**
 * @author maoyz
 */
public enum ResultStatusEnum {
    SUCCESS(200, "成功", "result.status.success"),
    NODATA(204, "无数据", "result.status.nodata"),
    DATA_EXISTED(400, "资源已存在", "result.status.data_exists"),
    DATA_NOT_EXISTS(404, "资源不存在", "result.status.data_not_exists"),
    UNAUTHORIZED(401, "未授权", "result.status.unauthorized"),
    FORBIDDEN(403, "服务不可用", "result.status.forbidden"),
    SIGN_ERR(405, "签名错误", "result.status.sign_err"),
    PARAM_ERR(415, "请求参数错误", "result.status.param_err"),
    FAIL(500, "服务器内部错误", "result.status.fail"),
    HOST_ERROR(501, "服务器无法识别请求", "result.status.host_error"),
    TIMEOUT(600, "请求超时", "result.status.timeout");

    private int status;
    private String describe;
    private String descKey;

    private ResultStatusEnum(int status, String describe, String descKey) {
        this.status = status;
        this.describe = describe;
        this.descKey = descKey;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescKey() {
        return this.descKey;
    }

    public void setDescKey(String descKey) {
        this.descKey = descKey;
    }
}