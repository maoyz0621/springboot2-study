/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.page.service;

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
        PageInfo<List<User>> page = PageHelper.startPage(1, 1).doSelectPageInfo(() -> {
            try {
                userMapper.selectAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        log.info("page = {}", page);

        List<List<User>> list = page.getList();
        log.info("pageResult = {}", page.getList());
        return list.get(0);
    }
}