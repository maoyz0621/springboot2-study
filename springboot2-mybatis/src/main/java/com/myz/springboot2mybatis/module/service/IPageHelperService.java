/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.module.service;

import com.myz.springboot2mybatis.module.entity.UserEntity;


/**
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
public interface IPageHelperService extends BasePageHelperService{

    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(UserEntity record);

}
