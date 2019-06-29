/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.service;

import com.myz.springboot2elasticsearch.dao.UserReposiory;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author maoyz0621 on 19-1-30
 * @version: v1.0
 */
@Service
public class UserService {

    @Autowired
    private UserReposiory userReposiory;

    public void insert(UserEntity userEntity){
        userReposiory.save(userEntity);
    }

    public Optional<UserEntity> get(Long id){
        return userReposiory.findById(id);
    }
}
