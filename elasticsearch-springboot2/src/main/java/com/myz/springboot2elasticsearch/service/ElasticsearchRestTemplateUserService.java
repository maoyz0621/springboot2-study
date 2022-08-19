/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import com.myz.springboot2elasticsearch.es.constants.BizEsConstant;
import com.myz.springboot2elasticsearch.es.model.UserQueryCondition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用 ElasticsearchRestTemplate
 *
 * @author maoyz0621 on 2022/2/28
 * @version v1.0
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ElasticsearchRestTemplateUserService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    private final ObjectMapper objectMapper;

    /**
     * 新增数据 save()，实则底层是index()，全量覆盖更新
     *
     * @param userEntity
     */
    public void save(UserEntity userEntity) {
        // 实则 elasticsearchRestTemplate.index
        UserEntity save = elasticsearchRestTemplate.save(userEntity);
        log.info("{}", save);
    }

    /**
     * 保存 index() IndexQuery
     *
     * @param userEntity
     */
    public void index(UserEntity userEntity) {
        IndexQuery indexQuery = null;
        try {
            indexQuery = new IndexQueryBuilder()
                    // 设置id
                    .withId(userEntity.getId() + "")
                    // 设置路由
                    .withRouting(userEntity.getUserId() + "")
                    // 1. 设置实体，_source中包含："_class" : "com.myz.springboot2elasticsearch.entity.UserEntity",
                    .withObject(userEntity)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String index = elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("index = {}", index);
    }

    public void indexJson(UserEntity userEntity) {
        IndexQuery indexQuery = null;
        try {
            indexQuery = new IndexQueryBuilder()
                    // 设置id
                    .withId(userEntity.getId() + "")
                    // 设置路由
                    .withRouting(userEntity.getUserId() + "")
                    // 2. 设置json，new Gson().toJson
                    .withSource(objectMapper.writeValueAsString(userEntity))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String index = elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("index = {}", index);
    }

    /**
     * 批量新增数据
     *
     * @param userEntityList
     */
    public void bulkIndex(List<UserEntity> userEntityList) {
        List<IndexQuery> indexQueryList = Lists.newArrayList();
        try {
            for (UserEntity userEntity : userEntityList) {
                IndexQuery indexQuery = new IndexQueryBuilder()
                        // 设置id
                        .withId(userEntity.getId() + "")
                        // 设置路由
                        .withRouting(userEntity.getUserId() + "")
                        // 1. 设置实体，_source中包含："_class" : "com.myz.springboot2elasticsearch.entity.UserEntity",
                        .withObject(userEntity)
                        // 2. 设置json，new Gson().toJson
                        // .withSource(objectMapper.writeValueAsString(userEntity))
                        .build();
                indexQueryList.add(indexQuery);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<IndexedObjectInformation> bulkIndex = elasticsearchRestTemplate.bulkIndex(indexQueryList, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("bulkIndex = {}", bulkIndex);
    }

    public void deleteById(Long id) {
        elasticsearchRestTemplate.delete(id.toString(), IndexCoordinates.of(BizEsConstant.INDEX_USER));
    }

    /**
     * 根据id局部更新 UpdateQuery
     *
     * @param userEntity
     */
    public void update(UserEntity userEntity) {
        UpdateQuery updateQuery = null;
        try {
            updateQuery = UpdateQuery.builder(userEntity.getId() + "")
                    .withDocAsUpsert(true)
                    .withRouting(userEntity.getUserId() + "")
                    .withFetchSource(true)
                    .withDocument(Document.parse(new Gson().toJson(userEntity)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateResponse update = elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("update = {}", update.getResult());
        elasticsearchRestTemplate.indexOps(IndexCoordinates.of(BizEsConstant.INDEX_USER));
    }

    public void bulkUpdate(List<UserEntity> userEntityList) {
        List<UpdateQuery> updateQueryList = new ArrayList<>();
        try {
            for (UserEntity userEntity : userEntityList) {
                UpdateQuery updateQuery = UpdateQuery.builder(userEntity.getId() + "")
                        .withDocAsUpsert(true)
                        .withRouting(userEntity.getUserId() + "")
                        .withFetchSource(true)
                        .withDocument(Document.parse(new Gson().toJson(userEntity)))
                        .build();
                updateQueryList.add(updateQuery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        elasticsearchRestTemplate.bulkUpdate(updateQueryList, IndexCoordinates.of(BizEsConstant.INDEX_USER));

        elasticsearchRestTemplate.indexOps(IndexCoordinates.of(BizEsConstant.INDEX_USER));
    }

    public void getById(Long id) {
        UserEntity userEntity = elasticsearchRestTemplate.get(id.toString(), UserEntity.class, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("userEntity = {}", userEntity);
    }

    /**
     * 搜索 search()
     *
     * @param userEntity
     */
    public void search(UserEntity userEntity) {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("", ""))
                .build();
        SearchHits<UserEntity> search = elasticsearchRestTemplate.search(query, UserEntity.class);
        log.info("{}", search);
    }

    public List<UserEntity> searchByConditionTermQuery(UserQueryCondition queryCondition) {
        // 创建term查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("", queryCondition.getAge());
        NativeSearchQuery searchQuery = new NativeSearchQuery(termQueryBuilder);
        // 分页、排序
        searchQuery.setPageable(PageRequest.of(queryCondition.getPageNo(), queryCondition.getPageSize(), Sort.by(Sort.Direction.DESC, "birth")));
        searchQuery.setTrackTotalHits(true);
        SearchHits<UserEntity> result = elasticsearchRestTemplate.search(searchQuery, UserEntity.class, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("request= \r\n{}, \r\nresult={}", termQueryBuilder.toString(), result);

        List<UserEntity> all = Lists.newArrayList();
        long totalHits = result.getTotalHits();
        for (SearchHit<UserEntity> userEntitySearchHit : result) {
            UserEntity content = userEntitySearchHit.getContent();
            all.add(content);
        }

        log.info("totalHits= {}, all= {}", totalHits, all);

        return all;
    }

    /**
     * 分页查询
     *
     * @param queryCondition
     */
    public List<UserEntity> searchByConditionBoolQuery(UserQueryCondition queryCondition) {
        // 创建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                // must
                // .must(QueryBuilders.matchQuery("username", queryCondition.getUsername()))
                // .filter(QueryBuilders.matchQuery("username", queryCondition.getUsername()))
                .must(QueryBuilders.termQuery("age", queryCondition.getAge()));
        NativeSearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
        // 分页、排序
        searchQuery.setPageable(PageRequest.of(queryCondition.getPageNo(), queryCondition.getPageSize(), Sort.by(Sort.Direction.DESC, "birth")));
        searchQuery.setTrackTotalHits(true);
        SearchHits<UserEntity> result = elasticsearchRestTemplate.search(searchQuery, UserEntity.class, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("request= \r\n{}, \r\nresult={}", boolQueryBuilder.toString(), result);

        List<UserEntity> all = Lists.newArrayList();
        long totalHits = result.getTotalHits();
        for (SearchHit<UserEntity> userEntitySearchHit : result) {
            UserEntity content = userEntitySearchHit.getContent();
            all.add(content);
        }

        log.info("totalHits= {}, all= {}", totalHits, all);
        return all;
    }

    public List<UserEntity> searchByConditionBoolQuery1(UserQueryCondition queryCondition) {
        // 创建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                // must
                // .must(QueryBuilders.matchQuery("username", queryCondition.getUsername()))
                .must(QueryBuilders.termQuery("age", queryCondition.getAge()));
        NativeSearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
        // 分页、排序
        searchQuery.setPageable(PageRequest.of(queryCondition.getPageNo(), queryCondition.getPageSize(), Sort.by(Sort.Direction.DESC, "birth")));
        searchQuery.setTrackTotalHits(true);
        SearchHits<UserEntity> result = elasticsearchRestTemplate.search(searchQuery, UserEntity.class, IndexCoordinates.of(BizEsConstant.INDEX_USER));
        log.info("request= \r\n{}, \r\nresult={}", boolQueryBuilder.toString(), result);

        Aggregations aggregations = result.getAggregations();

        List<UserEntity> all = Lists.newArrayList();
        long totalHits = result.getTotalHits();
        for (SearchHit<UserEntity> userEntitySearchHit : result) {
            UserEntity content = userEntitySearchHit.getContent();
            all.add(content);
        }

        log.info("totalHits= {}, all= {}", totalHits, all);

        return all;
    }

}