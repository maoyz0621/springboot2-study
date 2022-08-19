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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author maoyz0621 on 2022/3/22
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestHighLevelClientUserServiceTest {

    @Autowired
    RestHighLevelClientUserService restHighLevelClientUserService;

    @Test
    public void save() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(7L);
        userEntity.setAge(12);
        userEntity.setAddress("江苏");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸", "妈妈", "妻子"));
        userEntity.setUsername("abc");
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        restHighLevelClientUserService.save(userEntity);
    }

    @Test
    public void update() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(6L);
        userEntity.setAge(20);
        userEntity.setUserId(10002L);
        restHighLevelClientUserService.update(userEntity);
    }

    @Test
    public void bulkUpdate() {
        List<UserEntity> list = Lists.newArrayList();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(10L);
        userEntity.setAge(11);
        userEntity.setAddress("江苏1");
        userEntity.setBirth(new Date());
        userEntity.setBirth1(new Date());
        userEntity.setFamilyMembers(new FamilyMembersEntity("爸爸1", "妈妈1", "妻子1"));
        userEntity.setUsername("abc");
        userEntity.setBirth1(new Date());
        userEntity.setUserId(10002L);
        userEntity.setGoods(Lists.newArrayList("a", "b"));
        userEntity.setTelephone("151515151111");
        list.add(userEntity);

        UserEntity userEntity1 = (UserEntity) userEntity.clone();
        userEntity1.setId(11L);
        list.add(userEntity1);

        UserEntity userEntity2 = (UserEntity) userEntity.clone();
        userEntity2.setId(12L);
        list.add(userEntity2);
        restHighLevelClientUserService.bulkUpdate(list);
    }

    @Test
    public void deleteById() {
        restHighLevelClientUserService.deleteById(20L, "10002");
    }

    @Test
    public void getById() {
        restHighLevelClientUserService.getById(21L, "10002");
    }

    @Test
    public void searchByConditionQuery() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAge(11)
                .setMen(true)
                .setUserId(10002L);
        restHighLevelClientUserService.searchByConditionQuery(queryCondition);
    }

    @Test
    public void searchByConditionQueryTerms() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAddress("江苏")
                .setGoods(Lists.newArrayList("a", "b", "c"))
                .setMen(true)
                .setUserId(10002L);
        restHighLevelClientUserService.searchByConditionQueryTerms(queryCondition);
    }

    @Test
    public void searchByConditionFuzzyQuery() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAddress("江苏")
                .setMen(true)
                .setUserId(10002L);
        restHighLevelClientUserService.searchByConditionFuzzyQuery(queryCondition);
    }

    @Test
    public void searchByConditionWildcardQuery() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAddress("江")
                .setMen(true)
                .setUserId(10002L);
        restHighLevelClientUserService.searchByConditionWildcardQuery(queryCondition);
    }

    @Test
    public void searchByConditionRangeQuery() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAddress("江")
                .setMen(true)
                .setBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-03-24 20:46:22"))
                .setUserId(10002L);
        restHighLevelClientUserService.searchByConditionRangeQuery(queryCondition);
    }

    @Test
    public void searchByConditionBoolQueryFilter() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAge(11)
                .setBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-03-24 20:46:22"))
                .setGoods(Lists.newArrayList("a", "b", "c"))
                .setUserId(10002L);
        restHighLevelClientUserService.searchByConditionBoolQueryFilter(queryCondition);
    }

    @Test
    public void searchByConditionBoolQuery() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAge(11)
                .setBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-03-24 20:46:22"))
                .setGoods(Lists.newArrayList("a1", "b1", "c1"))
                .setTelephone("151515151111")
                .setUserId(10002L);
        restHighLevelClientUserService.searchByConditionBoolQuery(queryCondition);
    }

    @Test
    public void searchByConditionScroll() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAge(11)
                .setBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-03-24 20:46:22"))
                .setGoods(Lists.newArrayList("a1", "b1", "c1"))
                .setTelephone("151515151111")
                .setUserId(10002L);
        queryCondition.setPageSize(2);
        restHighLevelClientUserService.searchByConditionScroll(queryCondition);
    }

    @Test
    public void searchByConditionSearchAfter() throws ParseException {
        UserQueryCondition queryCondition = new UserQueryCondition()
                .setAge(11)
                .setBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-03-24 20:46:22"))
                .setGoods(Lists.newArrayList("a1", "b1", "c1"))
                .setTelephone("151515151111")
                .setUserId(10002L);
        queryCondition.setPageSize(2);
        restHighLevelClientUserService.searchByConditionSearchAfter(queryCondition);
    }
}