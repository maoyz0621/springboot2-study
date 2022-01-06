package com.myz.springboot2.mybatisplus34.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.myz.springboot2.mybatisplus34.entity.UserEntity;
import com.myz.springboot2.mybatisplus34.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author maoyz0621 on 2021/12/14
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;


    @Test
    public void testInsert(){
        userMapper.insert(new UserEntity().setAge(11).setEmail("a1").setGrade(null));
    }

    /**
     * update
     */
    @Test
    public void testUpdate() {
        // 重新赋值
        UserEntity entity = new UserEntity().setAge(11).setEmail("a1").setGrade(null);
        // where条件
        UserEntity entityWhere = new UserEntity().setAge(10).setEmail("a");
        Wrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate(entityWhere);
        userMapper.update(entity, updateWrapper);
    }

    /**
     * 局部更新 UpdateWrapper
     * UPDATE user SET created_by=? WHERE age=? AND email=?
     */
    @Test
    public void testUpdateWrapper() {
        // where条件
        UserEntity entityWhere = new UserEntity().setAge(10).setEmail("a");
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>(entityWhere);
        updateWrapper.set("created_by", "maoyz");
        updateWrapper.set("age", 10);
        userMapper.update(null, updateWrapper);
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
        userMapper.update(null, updateWrapper);
    }

    /**
     * 根据id更新
     * UPDATE user SET age = ?, email = ?  AND id = ?
     * 排除值为null的
     */
    @Test
    public void testUpdateById() {
        UserEntity entity = new UserEntity().setId(1L).setAge(10).setEmail("a");
        userMapper.updateById(entity);
    }

}