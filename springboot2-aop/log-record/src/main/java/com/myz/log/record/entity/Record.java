/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.entity;

import java.io.Serializable;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class Record implements Serializable {

    /**
     * 成功模板
     */
    private String success = "";

    /**
     * 失败模板
     */
    private String fail = "";

    /**
     * 操作用户
     */
    private String operator = "";

    /**
     * 业务编号
     */
    private String bizNo = "";

    /**
     * 分类
     */
    private String category = "";

    /**
     * 详细信息/备注
     */
    private String detail = "";

    /**
     * 条件，String 类型 Boolean 值，表示是否要记录此日志，"true":记录   "false": 不记录
     *
     * @see LogRecord#condition()
     */
    private String condition = "";

    /**
     * 是否完成执行，true：方法执行完成  false：方法执行过程中出错
     */
    private boolean complete = false;

    private long timestamp;

    public Record() {

    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Record{");
        sb.append("success='").append(success).append('\'');
        sb.append(", fail='").append(fail).append('\'');
        sb.append(", operator='").append(operator).append('\'');
        sb.append(", bizNo='").append(bizNo).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", condition='").append(condition).append('\'');
        sb.append(", complete=").append(complete);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}