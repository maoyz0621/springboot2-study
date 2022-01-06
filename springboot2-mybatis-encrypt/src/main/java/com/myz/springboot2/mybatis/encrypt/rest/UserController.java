/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.rest;

import com.myz.springboot2.mybatis.encrypt.domain.UserMapper;
import com.myz.springboot2.mybatis.encrypt.domain.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author maoyz0621 on 2021/10/25
 * @version v1.0
 */
@RestController
public class UserController {

    @Resource
    UserMapper userMapper;

    @GetMapping("/a")
    public String a() throws SQLException {
        List<User> users = userMapper.selectAll();
        return users.toString();
    }

    @GetMapping("/b")
    public String b() throws SQLException {
        User user = userMapper.selectById(1L);
        return user.toString();
    }

    @GetMapping("/insert")
    public String insert() throws SQLException {
        userMapper.insertUser(user());
        return "Success";
    }

    @GetMapping("/insert1")
    public String batchInsert() throws SQLException {
        List<User> users = new ArrayList<>();
        users.add(user());
        users.add(user());
        userMapper.batchInsert(users);
        return "Success";
    }

    @GetMapping("/update")
    public String update() throws SQLException {
        User user = new User();
        user.setId(11L);
        user.setEmail("123456@qq.com");
        userMapper.updateUser(user);
        return null;
    }

    private User user() {
        User user = new User();
        user.setEmail("123@qq.com");
        user.setGender("mail");
        user.setLastName("haha" + new Random().nextLong());
        return user;
    }
}