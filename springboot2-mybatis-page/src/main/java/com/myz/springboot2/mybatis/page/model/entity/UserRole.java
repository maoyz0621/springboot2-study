/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.mybatis.page.model.entity;

/**
 * @author maoyz on 2018/8/15
 * @version: v1.0
 */
public class UserRole {

    private User user;
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user=" + user +
                ", role=" + role +
                '}';
    }
}
