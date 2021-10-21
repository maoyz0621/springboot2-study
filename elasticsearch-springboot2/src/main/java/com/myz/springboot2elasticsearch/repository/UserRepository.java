// /**
//  * Copyright 2019 asiainfo Inc.
//  **/
package com.myz.springboot2elasticsearch.repository;

import com.myz.springboot2elasticsearch.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maoyz0621 on 19-1-30
 * @version v1.0
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<UserEntity, Long> {
}
