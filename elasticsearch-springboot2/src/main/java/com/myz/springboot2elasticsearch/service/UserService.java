/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.service;

import com.myz.springboot2elasticsearch.dao.UserRepository;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author maoyz0621 on 19-1-30
 * @version v1.0
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;



    public void insert(UserEntity userEntity){
        UserEntity save = userRepository.save(userEntity);

        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        // SearchQuery searchQuery = new NativeSearchQuery(queryBuilder)
        //         .setPageable(PageRequest.of(1,2, SortOrder.DESC));  // 分页
        //
        // userRepository.search()
    }

    public Optional<UserEntity> get(Long id){userRepository.findById(id);
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity;
    }
}
