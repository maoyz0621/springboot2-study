/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.mapper.business1;

import com.myz.springboot2.entity.UserEnity;

import java.util.List;

/**
 * @author maoyz on 18-10-25
 * @version: v1.0
 */
public interface UserMapper1 {

    int insert(UserEnity userEnity);

    List<UserEnity> getAll();
}
