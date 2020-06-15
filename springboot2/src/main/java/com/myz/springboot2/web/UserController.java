/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.web;

import com.myz.springboot2.entity.UserEnity;
import com.myz.springboot2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz on 18-10-25
 * @version: v1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private IUserService userService;

    @Autowired
    @Qualifier("userService1")
    private IUserService userService1;

    @PostMapping("/add")
    public Map add(String name) {
        UserEnity userEnity = new UserEnity();
        userEnity.setUsername("myz");
        userEnity.setAge(10);
        userEnity.setBirth(new Date(System.currentTimeMillis()));
        userService.add(userEnity);
        return null;
    }

    @PostMapping("/multiAdd")
    public Map multiAdd(String name) {
        UserEnity userEnity = new UserEnity();
        userEnity.setUsername("myz");
        userEnity.setAge(11);
        userEnity.setBirth(new Date(System.currentTimeMillis()));
        userService1.add(userEnity);
        return null;
    }

    @PostMapping("/list")
    public Map list() {
        List<UserEnity> all = userService.getAll();
        Map map = new HashMap();
        map.put("list", all);
        return map;
    }
}
