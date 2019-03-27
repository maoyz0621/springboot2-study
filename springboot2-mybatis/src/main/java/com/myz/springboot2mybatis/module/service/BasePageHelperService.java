/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.module.service;

import com.myz.springboot2mybatis.common.page.MyPage;
import com.myz.springboot2mybatis.common.page.query.ConcreteQuery;

import java.util.List;

/**
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
public interface BasePageHelperService<T> {

    /**
     * 返回所以数据
     */
    List<T> selectAll(ConcreteQuery qry);

    /**
     * 使用分页返回数据
     */
    MyPage<T> selectAllWithPage(ConcreteQuery qry);

}
