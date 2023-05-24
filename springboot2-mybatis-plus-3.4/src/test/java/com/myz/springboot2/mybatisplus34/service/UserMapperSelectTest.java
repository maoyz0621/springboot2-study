package com.myz.springboot2.mybatisplus34.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myz.springboot2.mybatisplus34.entity.UserEntity;
import com.myz.springboot2.mybatisplus34.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author maoyz0621 on 2021/12/14
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperSelectTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelectList1() {
        List<UserEntity> userEntities = userMapper.selectList(null);
        System.out.println(userEntities);
    }

    /**
     * WHERE age = ? AND email = ? AND tenant_id = null
     * 默认拼接 AND tenant_id = null
     */
    @Test
    public void testSelectList() {
        UserEntity entity = new UserEntity().setAge(10).setEmail("a");
        Wrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(entity);
        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        System.out.println(userEntities);
    }

    /**
     * WHERE (age = ? AND age IS NOT NULL AND FIND_IN_SET(',', age)) AND tenant_id = 65231313678678678
     */
    @Test
    public void testSelectApply() {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .apply(true, "age is not null and FIND_IN_SET (',', age)")
                .eq(UserEntity::getAge, 10);
        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        System.out.println(userEntities);
    }

    /**
     * WHERE (age = ? AND email = ?)
     */
    @Test
    public void testSelectListEq() {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserEntity::getAge, 10).eq(UserEntity::getEmail, "a");
        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        System.out.println(userEntities);
    }

    /**
     * 分页查询
     * SELECT COUNT(*)
     * <p>
     * SELECT id, name, age, age_enum, gender
     * LIMIT 2
     */
    @Test
    public void testSelectListPage() {
        UserEntity entity = new UserEntity().setAge(11);
        Wrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(entity);
        IPage<UserEntity> page = new Page<>();
        page.setCurrent(1L);
        page.setSize(2L);
        IPage<UserEntity> page1 = userMapper.selectPage(page, queryWrapper);
        System.out.println(page1.getRecords() + "/r/n" + page1.getTotal());
    }

}