/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.service;

import com.myz.springboot2.entity.UserEnity;
import com.myz.springboot2.mapper.business1.UserMapper1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author maoyz on 18-10-25
 * @version: v1.0
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper1 userMapper1;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(UserEnity userEnity) {
        int count = userMapper1.insert(userEnity);
        // 模拟异常情况
        int i = 1 / 0;
        System.out.println("add() ==>" + count);
    }

    @Override
    public List<UserEnity> getAll() {
        List<UserEnity> enities = userMapper1.getAll();
        return enities;
    }
}
