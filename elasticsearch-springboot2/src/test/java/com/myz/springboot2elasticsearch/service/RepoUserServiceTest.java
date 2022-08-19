package com.myz.springboot2elasticsearch.service;

import com.google.common.collect.Lists;
import com.myz.springboot2elasticsearch.entity.FamilyMembersEntity;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author maoyz0621 on 2022/4/3
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepoUserServiceTest {

    @Resource
    RepoUserService repoUserService;

    @Test
    public void index() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(33L);
        userEntity.setAge(32);
        userEntity.setAddress("安徽");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        userEntity.setUsername("abc");
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        repoUserService.index(userEntity);
    }

    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(6L);
        userEntity.setAge(12);
        // userEntity.setAddress("江苏");
        // userEntity.setBirth(new Date());
        // userEntity.setBirth1(new Date());
        // userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        // userEntity.setUsername("abc");
        // userEntity.setUserId(10002L);
        // userEntity.setGoods(Lists.newArrayList("a", "b"));
        // userEntity.setTelephone("151515151111");
        repoUserService.insert(userEntity);
    }

    @Test
    public void update() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(6L);
        userEntity.setAge(12);
        userEntity.setAddress("江苏");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        userEntity.setUsername("abc");
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        repoUserService.update(userEntity);
    }

    @Test
    public void get() {
    }
}