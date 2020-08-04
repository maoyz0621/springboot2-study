/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-28 18:07  Inc. All rights reserved.
 */
package com.myz.springboot2.common.data;

/**
 * @author maoyz
 */
public abstract class BaseResult extends DTO {

    // 状态码：1成功，其他为失败
    private int code;

    // 成功为success，其他为失败原因
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}