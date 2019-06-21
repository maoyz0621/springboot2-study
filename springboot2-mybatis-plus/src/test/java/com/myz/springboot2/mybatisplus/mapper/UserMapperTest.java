package com.myz.springboot2.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myz.springboot2.mybatisplus.Springboot2MybatisPlusApplicationTests;
import com.myz.springboot2.mybatisplus.enums.AgeEnum;
import com.myz.springboot2.mybatisplus.enums.GenderEnum;
import com.myz.springboot2.mybatisplus.enums.GradeEnum;
import com.myz.springboot2.mybatisplus.pojo.UserPO;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


/**
 * @author maoyz0621 on 19-4-9
 * @version: v1.0
 */
public class UserMapperTest extends Springboot2MybatisPlusApplicationTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 测试查询所有selectList()
     */
    @Test
    public void testSelect() {
        System.out.println("******************************");
        List<UserPO> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 逻辑删除1
     */
    @Test
    public void testLogicDeleteById() {
        System.out.println("******************************");
        int i = userMapper.deleteById(1);
        List<UserPO> users = userMapper.selectList(null);
        users.forEach(System.out::println);
        System.out.println("*************** 查询所有 ***************");
        List<UserPO> users1 = userMapper.selectAll(null);
        users1.forEach(System.out::println);
    }

    /**
     * 逻辑删除批量
     */
    @Test
    public void testLogicDeleteBatchId() {
        System.out.println("******************************");
        userMapper.deleteBatchIds(Arrays.asList("1", "2"));
        List<UserPO> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 逻辑删除
     */
    @Test
    public void testLogicDelete() {
        System.out.println("******************************");
        userMapper.delete(new QueryWrapper<UserPO>().eq("age", 18));
        List<UserPO> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void select() {
        System.out.println("******************************");
        UserPO user = new UserPO();
        user.setAge(10);
        user.setEmail("111111@qq.com");
        user.setAgeEnum(AgeEnum.ONE);
        user.setGender(GenderEnum.MAIL);
        user.setGrade(GradeEnum.SECONDORY);
        int insert = userMapper.insert(user);
        // 获取自增ID值
        System.out.println(user.getId());
        List<UserPO> users1 = userMapper.selectAll(null);
        users1.forEach(System.out::println);
    }

    @Test
    public void selectFieldFill() {
        System.out.println("******************************");
        UserPO user = new UserPO();
        user.setAge(10);
        user.setEmail("111111@qq.com");
        user.setAgeEnum(AgeEnum.ONE);
        user.setGender(GenderEnum.MAIL);
        user.setGrade(GradeEnum.SECONDORY);
        int insert = userMapper.insert(user);
        UserPO userPO = userMapper.selectById(user.getId());
        System.out.println("*************** fillField insert ***************");
        // UserPO(name=null, email=111111@qq.com, age=10, ageEnum=ONE, grade=SECONDORY, gender=MAIL, isDelete=0, operator=maoyz-insert)
        System.out.println(userPO);
        userMapper.update(user, new QueryWrapper<UserPO>().eq("id",2));
        // UserPO(name=Jack, email=111111@qq.com, age=10, ageEnum=ONE, grade=SECONDORY, gender=MAIL, isDelete=0, operator=maoyz-update)
        UserPO userPO2 = userMapper.selectById(2);
        System.out.println("*************** fillField update ***************");
        System.out.println(userPO2);
    }

}