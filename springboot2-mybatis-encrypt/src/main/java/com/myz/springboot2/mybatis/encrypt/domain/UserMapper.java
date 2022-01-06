package com.myz.springboot2.mybatis.encrypt.domain;


import com.myz.springboot2.mybatis.encrypt.domain.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    /*
     * 查询所有
     */
    List<User> selectAll();

    /*
     * 查询所有
     */
    List<User> selectAllByName(String lastName);

    /**
     * 查询结果返回一条map
     */
    Map<String, User> selectReturnMap(Long id);

    /**
     * 查询结果返回多条map
     * /@MapKey()　map中的key值
     */
    @MapKey("lastName")
    Map<String, User> selectReturnMaps(String lastName);


    /**
     * 　根据id查询
     */
    User selectById(Long id);

    /**
     * 参数不同的同名方法
     */
    User selectById(Long id, String lastName);

    /**
     * 　根据id和gender查询
     * 多参数查询@Param("")
     */
    User selectByIdAndGender(@Param("id") Long id, @Param("gender") String gender);

    /**
     * 根据pojo属性查询
     * #{属性名}
     */
    User selectByPOJO(User user);

    /**
     * 根据Map属性查询
     * #{key}
     */
    User selectByMap(Map<String, Object> map);

    /*
     *　添加
     */
    int insertUser(User user);

    int batchInsert(@Param("users") List<User> user);

    /*
     * 更新
     */
    int updateUser(User user);

    /*
     * 删除
     */
    int deleteUser(Long id);
}
