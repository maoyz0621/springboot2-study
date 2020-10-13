/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.page.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myz.springboot2.mybatis.page.model.dao.UserMapper;
import com.myz.springboot2.mybatis.page.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author maoyz0621 on 2020/9/23
 * @version v1.0
 */
@Slf4j
@Service
public class PageServiceImpl implements IPageService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<User> selectAllPage() throws SQLException {
        Page<List<User>> page = PageHelper.startPage(1, 1);
        log.info("page = {}", page);
        List<User> users = userMapper.selectAll();

        log.info("pageResult = {}", page.getResult());
        log.info("PageInfo = {}", new PageInfo<>(users));
        return null;
    }
}