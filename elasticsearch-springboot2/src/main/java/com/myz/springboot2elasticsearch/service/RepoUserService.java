/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.service;

import com.myz.springboot2elasticsearch.entity.UserEntity;
import com.myz.springboot2elasticsearch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

// import com.myz.springboot2elasticsearch.repository.UserRepository;

/**
 * @author maoyz0621 on 19-1-30
 * @version v1.0
 */
@Slf4j
@Service
public class RepoUserService {

    @Resource
    private UserRepository userRepository;

    /**
     * org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository#save()
     * org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate#index()
     *
     * @param userEntity
     */
    public void index(UserEntity userEntity) {
        UserEntity save = userRepository.index(userEntity);
    }

    public void insert(UserEntity userEntity) {
        UserEntity save = userRepository.save(userEntity);
    }

    public void update(UserEntity userEntity) {
        UserEntity save = userRepository.save(userEntity);
    }


    public Optional<UserEntity> get(Long id) {
        userRepository.findById(id);
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity;
    }
}
