/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.page.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myz.springboot2mybatis.common.page.MyPage;
import com.myz.springboot2mybatis.common.page.query.AbstractQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;

/**
 * pagehelper的使用核心是：调用PageHelper.startPage(pageNum, pageSize,requireTotalCount)方法设置查询记录起始地址后，
 * 然后马上调用mapper类的方法，得到返回列表List，使用（ PageInfo pageInfo = new PageInfo(list)）包装list的PageInfo，PageInfo包含本次查询的信息，
 * 包括本次查询的总数，然后将Model转化为Dto类
 *
 *
 * 分页的回调函数
 *
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
public class PageCallBackUtil {

    private static final Logger logger =  LoggerFactory.getLogger(PageCallBackUtil.class);

    /**
     * 封装公共PageHelper的操作
     */
    public static <T> MyPage<T> selectRtnPage(AbstractQuery qry, IPageHelperPageCallBack callBack){
        Assert.notNull(qry, "qry can't be null!");
        Assert.notNull(callBack, "callBack cant' be null!");

        setPageHelperStartPage(qry);

        List<T> list = callBack.select();

        // 分页时，实际返回的结果list类型是Page<E>
        if (!(list instanceof Page)){
            throw new RuntimeException("list must be 'com.github.pagehelper.Page', now is " + list.getClass().getCanonicalName());
        }

        MyPage<T> page = new MyPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);

        logger.debug("pageInfo = {}", pageInfo);

        page.setTotalCount((int) pageInfo.getTotal());
        page.setRows(pageInfo.getList());

        return page;
    }

    /**
     * 设置PageHelper的startPage
     */
    private static void setPageHelperStartPage(AbstractQuery qry) {
        Integer pageNum = qry.getPageNum();
        pageNum = (pageNum == null) ? AbstractQuery.DEFAULT_PAGE_NUM : pageNum;

        Integer pageSize = qry.getPageSize();
        pageSize = (pageSize == null) ? AbstractQuery.DEFAULT_PAGE_SIZE : pageSize;

        Boolean requireTotalCount = qry.getRequireTotalCount();
        requireTotalCount = (requireTotalCount == null) ? AbstractQuery.DEFAULT_REQUIRE_TOTAL_COUNT : requireTotalCount;

        logger.debug("pageNum = {}, pageSize = {}, requireTotalCount = {}", pageNum, pageSize, requireTotalCount);

        // 执行PageHelper的分页查询
        PageHelper.startPage(pageNum, pageSize, requireTotalCount);

    }
 }
