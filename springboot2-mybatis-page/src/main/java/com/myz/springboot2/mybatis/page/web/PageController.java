/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.page.web;

import com.myz.springboot2.mybatis.page.model.dao.UserMapper;
import com.myz.springboot2.mybatis.page.model.entity.User;
import com.myz.springboot2.mybatis.page.service.IPageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author maoyz0621 on 2020/9/23
 * @version v1.0
 */
@RestController
public class PageController {

    @Resource
    IPageService pageService;

    @Resource
    UserMapper userMapper;

    @GetMapping("/a")
    public String a() throws SQLException {
        List<User> users = pageService.selectAllPage();
        return users.toString();
    }

    @GetMapping("/insert")
    public String insert() throws SQLException {
        User user = new User();
        user.setId(11L);
        user.setEmail("123@qq.com");
        user.setGender("mail");
        user.setLastName("haha");
        userMapper.insertUser(user);
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

    @GetMapping("/select")
    public String select() throws SQLException {
        User user = userMapper.selectById(7L);
        return user.toString();
    }
}