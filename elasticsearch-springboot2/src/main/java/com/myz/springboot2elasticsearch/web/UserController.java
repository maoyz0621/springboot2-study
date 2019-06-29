/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.web;

import com.myz.springboot2elasticsearch.dao.UserReposiory;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import com.myz.springboot2elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author maoyz0621 on 19-1-30
 * @version: v1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public void insert(@RequestBody UserEntity userEntity) {
        userService.insert(userEntity);
    }

    @GetMapping("/get/{id}")
    public Optional<UserEntity> get(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping("/hello")
    public String index() {
        return "hello";
    }
}
