/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.dao;

import com.myz.springboot2elasticsearch.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author maoyz0621 on 19-1-30
 * @version: v1.0
 */
public interface UserReposiory extends CrudRepository<UserEntity, Long> {
}
