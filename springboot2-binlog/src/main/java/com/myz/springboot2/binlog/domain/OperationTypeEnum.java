/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.domain;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author maoyz0621 on 2021/8/25
 * @version v1.0
 */
public enum OperationTypeEnum {
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    OTHER("OTHER");

    private final String type;

    OperationTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static OperationTypeEnum convert(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return INSERT;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}