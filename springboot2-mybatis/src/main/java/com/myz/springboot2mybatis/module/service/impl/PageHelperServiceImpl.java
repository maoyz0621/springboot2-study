/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.module.service.impl;

import com.myz.springboot2mybatis.common.page.MyPage;
import com.myz.springboot2mybatis.common.page.query.ConcreteQuery;
import com.myz.springboot2mybatis.common.page.util.IPageHelperPageCallBack;
import com.myz.springboot2mybatis.common.page.util.PageCallBackUtil;
import com.myz.springboot2mybatis.module.entity.UserEntity;
import com.myz.springboot2mybatis.module.mapper.UserEntityMapper;
import com.myz.springboot2mybatis.module.service.IPageHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
@Service
public class PageHelperServiceImpl implements IPageHelperService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        Assert.notNull(id, "id can not null");
        return userEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(UserEntity record) {
        int count = userEntityMapper.insertSelective(record);
        return count;
    }

    @Override
    public UserEntity selectByPrimaryKey(Integer id) {
        return userEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UserEntity record) {
        return userEntityMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UserEntity> selectAll(ConcreteQuery qry) {
        return userEntityMapper.selectByExample(null);
    }

    @Override
    public MyPage<UserEntity> selectAllWithPage(ConcreteQuery qry) {
        if (qry == null){
            qry = new ConcreteQuery();
        }

        MyPage<UserEntity> page = PageCallBackUtil.selectRtnPage(qry, new IPageHelperPageCallBack() {

            @Override
            public  List<UserEntity> select() {
                return userEntityMapper.selectByExample(null);
            }
        });

        return page;
    }
}
