/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import com.myz.springboot2elasticsearch.es.constants.BizEsConstant;
import com.myz.springboot2elasticsearch.es.model.UserQueryCondition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.join.query.ParentIdQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 父子文档查询
 *
 * @author maoyz0621 on 2022/4/3
 * @version v1.0
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ParentChildService {

    private final RestHighLevelClient restHighLevelClient;
    @Autowired
    @Qualifier("objectMapperNoNull")
    private ObjectMapper objectMapper;

    /**
     * Has-Child
     * @param queryCondition
     */
    public void childQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            // 子查询
            HasChildQueryBuilder childQuery = JoinQueryBuilders.hasChildQuery("parent", QueryBuilders.matchQuery("", ""), ScoreMode.None);
            sourceBuilder.query(childQuery);
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 根据parentId进行查询
     * Parent-Id-Query
     * @param queryCondition
     */
    public void parentIdQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            ParentIdQueryBuilder parentIdQueryBuilder = JoinQueryBuilders.parentId("", "");
            sourceBuilder.query(parentIdQueryBuilder);
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 根据parent中的条件，返回子文档集合
     * Has-Parent
     * @param queryCondition
     */
    public void parentQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            HasParentQueryBuilder parentQuery = JoinQueryBuilders.hasParentQuery("", QueryBuilders.matchQuery("", ""), true);
            sourceBuilder.query(parentQuery);
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    private void readResponse(SearchResponse searchResponse) throws JsonProcessingException {
        SearchHits hits = searchResponse.getHits();
        // 总数量
        TotalHits totalHits = hits.getTotalHits();
        List<UserEntity> result = new ArrayList<>(hits.getHits().length);
        for (SearchHit hit : hits) {
            UserEntity userEntity = objectMapper.readValue(hit.getSourceAsString(), UserEntity.class);
            result.add(userEntity);
        }
        log.info("total={},\r\nsize={},\r\nresult={}", totalHits.value, result.size(), result);
    }

}