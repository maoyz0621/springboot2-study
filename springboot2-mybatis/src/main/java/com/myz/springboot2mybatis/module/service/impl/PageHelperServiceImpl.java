/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.module.service.impl;

import com.myz.springboot2mybatis.common.page.MyPage;
import com.myz.springboot2mybatis.common.page.query.ConcreteQuery;
import com.myz.springboot2mybatis.common.page.util.IPageHelperPageCallBack;
import com.myz.springboot2mybatis.common.page.util.PageCallBackUtil;
import com.myz.springboot2mybatis.module.entity.UserEntity;
import com.myz.springboot2mybatis.module.mapper.master.UserEntityMapper;
import com.myz.springboot2mybatis.module.mapper.slave.SlaveUserEntityMapper;
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
    private UserEntityMapper userEntityMapperMaster;

    @Autowired
    private SlaveUserEntityMapper slaveUserEntityMapperSlave;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        Assert.notNull(id, "id can not null");
        return userEntityMapperMaster.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(UserEntity record) {
        int count = userEntityMapperMaster.insertSelective(record);
        return count;
    }

    @Override
    public UserEntity selectByPrimaryKey(Integer id) {
        return slaveUserEntityMapperSlave.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UserEntity record) {
        return userEntityMapperMaster.updateByPrimaryKey(record);
    }

    @Override
    public List<UserEntity> selectAll(ConcreteQuery qry) {
        return slaveUserEntityMapperSlave.selectByExample(null);
    }

    @Override
    public MyPage<UserEntity> selectAllWithPage(ConcreteQuery qry) {
        if (qry == null) {
            qry = new ConcreteQuery();
        }

        MyPage<UserEntity> page = PageCallBackUtil.selectRtnPage(qry, new IPageHelperPageCallBack() {

            @Override
            public List<UserEntity> select() {
                return slaveUserEntityMapperSlave.selectByExample(null);
            }
        });

        return page;
    }
}
