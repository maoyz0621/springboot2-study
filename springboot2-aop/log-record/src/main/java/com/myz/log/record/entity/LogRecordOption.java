/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.entity;

import java.util.List;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class LogRecordOption {

    private String key;

    private String value;

    private boolean isTemplate = true;

    private List<String> functionNames;

    private boolean isBeforeExecute;

    public LogRecordOption() {
    }

    public LogRecordOption(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(boolean template) {
        isTemplate = template;
    }

    public List<String> getFunctionNames() {
        return functionNames;
    }

    public void setFunctionNames(List<String> functionNames) {
        this.functionNames = functionNames;
    }

    public boolean isBeforeExecute() {
        return isBeforeExecute;
    }

    public void setBeforeExecute(boolean beforeExecute) {
        isBeforeExecute = beforeExecute;
    }
}