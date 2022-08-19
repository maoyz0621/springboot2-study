/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2.mybatisplus34.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.myz.springboot2.mybatisplus34.entity.UserEntity;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author maoyz0621 on 2022/1/4
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserServiceTest {

    @Autowired
    IUserService userService;


    @Test
    public void testSave() {
        userService.save(new UserEntity().setAge(11).setEmail("save" + UUID.randomUUID().toString()).setGrade(null));
    }

    @Test
    public void testSaveBatch() {
        StopWatch stopWatch = StopWatch.createStarted();
        List<UserEntity> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            list.add(new UserEntity().setAge(11 + i).setEmail("save" + UUID.randomUUID().toString()).setGrade(null));
        }
        userService.saveBatch(list);
        System.out.println("saveBatch take " + stopWatch.getTime());
    }

    @Test
    public void testInsertBatch() {
        StopWatch stopWatch = StopWatch.createStarted();
        List<UserEntity> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            list.add(new UserEntity().setAge(11 + i).setEmail("save" + UUID.randomUUID().toString()).setGrade(null));
        }
        userService.insertBatch(list);
        System.out.println("insertBatch take " + stopWatch.getTime());
    }

    /**
     * 根据id更新
     * UPDATE user SET age = ?, email = ?  AND id = ?
     * 排除值为null的
     */
    @Test
    public void testUpdateById() {
        UserEntity entity = new UserEntity().setId(2L).setAge(11).setEmail("updateById" + UUID.randomUUID().toString()).setGrade(2);
        userService.updateById(entity);
    }

    @Test
    public void testSaveOrUpdate() {
        userService.saveOrUpdate(new UserEntity().setAge(12).setEmail("a1").setGrade(1));
        // 先执行SELECT查询操作
        userService.saveOrUpdate(new UserEntity().setId(1L).setAge(11).setEmail("save" + UUID.randomUUID().toString()).setGrade(2));

    }

    /**
     * update
     * WHERE tenant_id = ? AND age = ? AND email = ?
     */
    @Test
    public void testUpdateWrapper() {
        // 重新赋值
        UserEntity entity = new UserEntity().setAge(11).setEmail("a1").setGrade(null);
        // where条件
        UserEntity entityWhere = new UserEntity().setAge(10).setEmail("a");
        Wrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate(entityWhere);
        userService.update(entity, updateWrapper);
    }

    /**
     * 局部更新 UpdateWrapper
     * UPDATE user SET created_by=? WHERE age=? AND email=?
     */
    @Test
    public void testUpdateUpdateWrapper() {
        // where条件
        UserEntity entityWhere = new UserEntity().setAge(10).setEmail("a");
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>(entityWhere);

        // 设置值
        updateWrapper.set("created_by", "maoyz");
        updateWrapper.set("age", 10);
        userService.update(null, updateWrapper);
    }

    /**
     * 局部更新 LambdaUpdateWrapper
     * UPDATE user SET company_id=? WHERE age=? AND email=?
     */
    @Test
    public void testLambdaUpdateWrapper() {
        // where条件
        UserEntity entityWhere = new UserEntity().setAge(10);
        LambdaUpdateWrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate(entityWhere);
        // 拼接where 条件
        updateWrapper.eq(UserEntity::getEmail, "a");

        // 设置值
        updateWrapper.set(UserEntity::getCompanyId, 100000);
        userService.update(null, updateWrapper);
    }

    /////////////////////////////// remove start /////////////////////////////

    /**
     * DELETE FROM user
     */
    @Test
    public void testRemoveById() {
        userService.removeById(17198);
    }

    /**
     * DELETE FROM user WHERE tenant_id = 65231313678678678 AND id IN (?, ?)
     */
    @Test
    public void testRemoveByIds() {
        userService.removeByIds(Arrays.asList(17198, 17199));
    }

    @Test
    public void testRemoveByMap() {
        Map<String, Object> param = new HashMap<>();
        param.put("id", 17190L);
        userService.removeByMap(param);
    }

    /**
     * DELETE FROM user WHERE tenant_id = 65231313678678678 AND (age = ? AND email = ?)
     */
    @Test
    public void testRemove() {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserEntity::getAge, 10).eq(UserEntity::getEmail, "a");
        userService.remove(queryWrapper);
    }
    /////////////////////////////// remove start /////////////////////////////

}