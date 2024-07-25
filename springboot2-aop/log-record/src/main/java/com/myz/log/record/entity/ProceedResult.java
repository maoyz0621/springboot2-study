/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.entity;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */

public class ProceedResult {

    private boolean success;

    private Throwable throwable;

    private String errMsg;

    public ProceedResult(boolean success, Throwable exception, String errMsg) {
        this.success = success;
        this.throwable = exception;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}