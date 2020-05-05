/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.repository;

import com.myz.springboot2.entity.UserEnity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * jpa使用缓存
 *
 * @author maoyz on 2018/8/27
 * @version: v1.0
 */
@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<UserEnity, Serializable> {

    /**
     * 返回值将被加入缓存,在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问
     * 使用第一个参数作为缓存的key值 #p0
     */
    @Cacheable(key = "#p0")
    UserEnity findByAge(Integer age);

    /**
     * 每次都会真是调用函数，所以主要用于数据新增和修改操作上
     */
    @CachePut(key = "#p0.age")
    @Override
    UserEnity save(UserEnity user);

}
