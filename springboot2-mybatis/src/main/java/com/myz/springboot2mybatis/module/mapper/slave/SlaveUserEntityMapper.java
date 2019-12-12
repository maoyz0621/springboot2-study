package com.myz.springboot2mybatis.module.mapper.slave;

import com.myz.springboot2mybatis.module.entity.UserEntity;
import com.myz.springboot2mybatis.module.entity.UserEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlaveUserEntityMapper {
    long countByExample(UserEntityExample example);

    int deleteByExample(UserEntityExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    List<UserEntity> selectByExample(UserEntityExample example);

    UserEntity selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserEntity record, @Param("example") UserEntityExample example);

    int updateByExample(@Param("record") UserEntity record, @Param("example") UserEntityExample example);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}