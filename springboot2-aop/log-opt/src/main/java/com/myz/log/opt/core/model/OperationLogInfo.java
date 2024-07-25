/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.model;

import org.springframework.lang.Nullable;

import java.util.StringJoiner;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class OperationLogInfo {

    private BizCategoryEnum bizCategoryEnum;

    private String bizTarget;

    private Object bizNo;

    private Object diffSelector;

    public OperationLogInfo() {
    }

    public OperationLogInfo(BizCategoryEnum bizCategoryEnum, String bizTarget, Object bizNo, @Nullable Object diffSelector) {
        this.bizCategoryEnum = bizCategoryEnum;
        this.bizTarget = bizTarget;
        this.bizNo = bizNo;
        this.diffSelector = diffSelector;
    }

    public BizCategoryEnum getBizCategory() {
        return bizCategoryEnum;
    }

    public void setBizCategory(BizCategoryEnum bizCategoryEnum) {
        this.bizCategoryEnum = bizCategoryEnum;
    }

    public String getBizTarget() {
        return bizTarget;
    }

    public void setBizTarget(String bizTarget) {
        this.bizTarget = bizTarget;
    }

    public Object getBizNo() {
        return bizNo;
    }

    public void setBizNo(Object bizNo) {
        this.bizNo = bizNo;
    }

    public Object getDiffSelector() {
        return diffSelector;
    }

    public void setDiffSelector(Object diffSelector) {
        this.diffSelector = diffSelector;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("bizCategory=" + bizCategoryEnum)
                .add("bizTarget=" + bizTarget)
                .add("bizNo=" + bizNo)
                .add("diffSelector=" + diffSelector)
                .toString();
    }
}