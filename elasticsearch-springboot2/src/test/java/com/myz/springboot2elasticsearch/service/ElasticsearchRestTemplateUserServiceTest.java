package com.myz.springboot2elasticsearch.service;

import com.google.common.collect.Lists;
import com.myz.springboot2elasticsearch.entity.FamilyMembersEntity;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import com.myz.springboot2elasticsearch.es.model.UserQueryCondition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author maoyz0621 on 2022/2/28
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchRestTemplateUserServiceTest {
    @Autowired
    ElasticsearchRestTemplateUserService elasticsearchRestTemplateUserService;

    @Test
    public void save() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(40L);
        userEntity.setAge(12);
        userEntity.setAddress("江苏");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        userEntity.setUsername("abc");
        userEntity.setBirth1(new Date());
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        elasticsearchRestTemplateUserService.save(userEntity);
    }

    @Test
    public void index() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(20L);
        userEntity.setAge(12);
        userEntity.setAddress("江苏");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        userEntity.setUsername("abc");
        userEntity.setBirth1(new Date());
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        elasticsearchRestTemplateUserService.index(userEntity);
    }

    @Test
    public void indexJson() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(12L);
        userEntity.setAge(11);
        userEntity.setAddress("江苏");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        userEntity.setUsername("abc");
        userEntity.setBirth1(new Date());
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        elasticsearchRestTemplateUserService.indexJson(userEntity);
    }

    @Test
    public void bulkIndex() {
        List<UserEntity> list = Lists.newArrayList();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(20L);
        userEntity.setAge(11);
        userEntity.setAddress("江苏");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        userEntity.setUsername("abc");
        userEntity.setBirth1(new Date());
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        list.add(userEntity);

        UserEntity userEntity1 = (UserEntity) userEntity.clone();
        userEntity1.setId(21L);
        list.add(userEntity1);
        elasticsearchRestTemplateUserService.bulkIndex(list);
    }

    @Test
    public void deleteById() {
        elasticsearchRestTemplateUserService.deleteById(12L);
        elasticsearchRestTemplateUserService.getById(12L);
    }

    @Test
    public void update() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(12L);
        userEntity.setAge(30);
        userEntity.setUserId(1002L);
        elasticsearchRestTemplateUserService.update(userEntity);
    }

    @Test
    public void updateAll() {
    }

    @Test
    public void getById() {
        elasticsearchRestTemplateUserService.getById(12L);
    }

    @Test
    public void search() {
    }

    @Test
    public void searchByConditionBoolQuery() {
        UserQueryCondition queryCondition = new UserQueryCondition();
        queryCondition.setAge(30);
        elasticsearchRestTemplateUserService.searchByConditionBoolQuery(queryCondition);
    }
}