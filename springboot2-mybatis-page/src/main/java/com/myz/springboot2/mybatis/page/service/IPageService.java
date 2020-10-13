/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.page.service;

import com.myz.springboot2.mybatis.page.model.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @author maoyz0621 on 2020/9/23
 * @version v1.0
 */
public interface IPageService {

    List<User> selectAllPage() throws SQLException;
}