/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.service;

import com.myz.springboot2.entity.UserEnity;

import java.util.List;

/**
 * @author maoyz on 18-10-25
 * @version: v1.0
 */
public interface IUserService {

    void add(UserEnity userEnity);

    List<UserEnity> getAll();

}
