/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.myz.springboot2.mybatisplus.pojo.UserPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author maoyz0621 on 19-4-9
 * @version: v1.0
 */
public interface UserMapper extends BaseMapper<UserPO> {

    /**
     * 自定义
     * @param queryWrapper
     * @return
     */
    List<UserPO> selectAll(@Param(Constants.WRAPPER) Wrapper<UserPO> queryWrapper);
}
