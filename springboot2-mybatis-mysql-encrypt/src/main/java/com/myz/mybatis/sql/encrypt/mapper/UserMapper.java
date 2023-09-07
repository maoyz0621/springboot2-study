/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myz.mybatis.sql.encrypt.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author maoyz0621 on 2023/5/25
 * @version v1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}