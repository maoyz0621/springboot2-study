/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.service;

import com.myz.springboot2.entity.UserEnity;
import com.myz.springboot2.mapper.business1.UserMapper1;
import com.myz.springboot2.mapper.business2.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多数据源事务
 *
 * @author maoyz on 18-11-2
 * @version: v1.0
 */
@Service("userService1")
public class UserServiceMulti implements IUserService {

    @Resource
    private UserMapper1 userMapper1;

    @Resource
    private UserMapper2 userMapper2;

    @Transactional(transactionManager = "baseTransactionManager",
            rollbackFor = RuntimeException.class)
    @Override
    public void add(UserEnity userEnity) {
        int count1 = userMapper1.insert(userEnity);
        int count2 = userMapper2.insert(userEnity);
        // 模拟异常情况
        int i = 1 / 0;
        System.out.println("add() ==>" + count1 + "; " + count2);

    }

    @Override
    public List<UserEnity> getAll() {
        return null;
    }
}
