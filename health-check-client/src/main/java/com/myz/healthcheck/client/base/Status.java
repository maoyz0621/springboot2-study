/**
 * Copyright 2022 Inc.
 **/
package com.myz.healthcheck.client.base;

/**
 * @author maoyz0621 on 2022/5/8
 * @version v1.0
 */
public final class Status {

    public static final Status UNKNOWN = new Status("UNKNOWN");
    public static final Status UP = new Status("UP");
    public static final Status DOWN = new Status("DOWN");

    private final String code;
    private final String description;

    public Status(String code) {
        this(code, "");
    }

    public Status(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Status{");
        sb.append("code='").append(code).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}