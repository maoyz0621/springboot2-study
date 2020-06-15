/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.entity.datatable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DataTable返回页面参数
 *
 * @author maoyz on 2018/7/19
 * @version: v1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataTable<T> {
    /**
     * 请求服务器端次数
     */
    private int sEcho;
    /**
     * 必要。过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
     */
    private Long iTotalDisplayRecords;
    /**
     * 必要。即没有过滤的记录数（数据库里总共记录数）
     */
    private Long iTotalRecords;
    /**
     * 数据
     */
    private List<T> aaData;
    /**
     * 可选。你可以定义一个错误来描述服务器出了问题后的友好提示
     */
    private String error;
}
