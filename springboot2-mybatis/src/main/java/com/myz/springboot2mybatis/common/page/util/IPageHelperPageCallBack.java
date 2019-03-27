/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.page.util;

import java.util.List;

/**
 * 定义分页的回调方法，如果使用分页，则必须在这个方法里使用mapper的方法。
 * 此接口作为下文的PageCallBackUtil的参数
 *
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
public interface IPageHelperPageCallBack {

    /**
     * 使用分页，在这个方法里使用mapper的方法
     */
    <T> List<T> select();
}
