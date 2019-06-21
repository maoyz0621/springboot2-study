/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myz.springboot2.mybatisplus.pojo.UserPO;
import com.myz.springboot2.mybatisplus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author maoyz0621 on 19-4-9
 * @version: v1.0
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserPO> {

    @Autowired
    private UserMapper userMapper;
}
