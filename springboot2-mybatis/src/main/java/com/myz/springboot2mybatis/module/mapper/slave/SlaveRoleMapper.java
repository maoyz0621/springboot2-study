package com.myz.springboot2mybatis.module.mapper.slave;

import com.myz.springboot2mybatis.module.entity.Role;
import com.myz.springboot2mybatis.module.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlaveRoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}