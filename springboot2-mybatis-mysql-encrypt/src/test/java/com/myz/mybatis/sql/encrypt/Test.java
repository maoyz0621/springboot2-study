/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.myz.mybatis.sql.encrypt.entity.UserEntity;
import com.myz.mybatis.sql.encrypt.mapper.UserMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author maoyz0621 on 2023/5/25
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    UserMapper userMapper;

    @org.junit.Test
    public void all() {
        testSelectList1();
        testSelectList();
        testSelectList();
        testInsert();
        testUpdate();
        testUpdate();
    }

    @org.junit.Test
    public void testSelectList1() {
        List<UserEntity> userEntities = userMapper.selectList(null);
        System.out.println(userEntities);
    }


    /**
     * WHERE age = ? AND email = ? AND tenant_id = null
     * 默认拼接 AND tenant_id = null
     */
    @org.junit.Test
    public void testSelectList() {
        UserEntity entity = new UserEntity().setAge(10).setEmail("a");
        Wrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(entity);
        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        System.out.println(userEntities);
    }

    @org.junit.Test
    public void testInsert() {
        userMapper.insert(new UserEntity().setAge(11).setEmail("a1").setGrade(null));
    }

    /**
     * update
     * UPDATE user SET age = 11, email = "a1", last_modified_by = ?, last_modified_time = ?
     * WHERE tenant_id = 65231313678678678 AND age = 10 AND email = "a"
     */
    @org.junit.Test
    public void testUpdate() {
        // 重新赋值,null值不更新
        UserEntity entity = new UserEntity().setAge(11).setEmail("a1").setGrade(null);

        // where条件
        UserEntity entityWhere = new UserEntity().setAge(10).setEmail("a");
        LambdaUpdateWrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate(entityWhere);

        userMapper.update(entity, updateWrapper);
    }
}