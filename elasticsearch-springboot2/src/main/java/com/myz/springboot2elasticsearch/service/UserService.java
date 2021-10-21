/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.service;

import com.myz.springboot2elasticsearch.entity.UserEntity;
import com.myz.springboot2elasticsearch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

// import com.myz.springboot2elasticsearch.repository.UserRepository;

/**
 * @author maoyz0621 on 19-1-30
 * @version v1.0
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    protected RestHighLevelClient restHighLevelClient;

    public void insert(UserEntity userEntity) {
        UserEntity save = userRepository.save(userEntity);

        SearchRequest searchRequest = new SearchRequest("user");

        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("a", "2"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info(searchRequest.toString());
            log.info(searchResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<UserEntity> get(Long id) {
        userRepository.findById(id);
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity;
    }
}
