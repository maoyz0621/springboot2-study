/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.es.constants;

/**
 * @author maoyz0621 on 2022/4/21
 * @version v1.0
 */
public enum MemberTypeEnum {

    GrandParent("grand_parent", "1"),
    Parent("parent", "2"),
    Child("child", "3");

    private final String type;
    private final String level;

    MemberTypeEnum(String type, String level) {
        this.type = type;
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }
}