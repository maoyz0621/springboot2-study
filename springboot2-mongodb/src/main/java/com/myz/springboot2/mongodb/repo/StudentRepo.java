/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2.mongodb.repo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.myz.springboot2.mongodb.entity.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @author maoyz0621 on 2022/6/16
 * @version v1.0
 */
@Slf4j
@Repository
public class StudentRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增
     *
     * @param entity
     */
    public void insert(StudentEntity entity) {
        StudentEntity insert = mongoTemplate.insert(entity);
    }

    /**
     * 更新
     *
     * @param entity
     */
    public void update(StudentEntity entity) {
        Query query = new Query(Criteria.where("_id").is(entity.getId()));
        Update update = new Update().set("username", entity.getUsername());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, StudentEntity.class);
    }

    /**
     * 新增或更新
     *
     * @param entity
     */
    public void save(StudentEntity entity) {
        Query query = new Query(Criteria.where("_id").is(entity.getId()));
        Update update = new Update().set("username", entity.getUsername());
        UpdateResult updateResult = mongoTemplate.upsert(query, update, StudentEntity.class);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult deleteResult = mongoTemplate.remove(query, StudentEntity.class);
    }
}