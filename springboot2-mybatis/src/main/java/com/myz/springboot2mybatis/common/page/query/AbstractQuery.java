/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.page.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 定义分页的查询的页码信息
 *
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
@Setter
@Getter
public abstract class AbstractQuery implements Query {

    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 1;
    public static final boolean DEFAULT_REQUIRE_TOTAL_COUNT = false;

    private String id;
    /**
     * 第几页，首页为1
     */
    private Integer pageNum = 1;
    /**
     * 每页记录条数
     */
    private Integer pageSize = 10;
    /**
     * 是否需要记录总数
     */
    private Boolean requireTotalCount = Boolean.FALSE;

}
