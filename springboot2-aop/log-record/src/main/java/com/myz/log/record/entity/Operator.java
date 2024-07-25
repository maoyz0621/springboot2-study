/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.entity;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class Operator {

    private String name;

    private String username;

    public Operator() {
    }

    public Operator(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Operator{");
        sb.append("name='").append(name).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}