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
 * 封装dataTable数据
 *
 * @author maoyz on 2018/7/19
 * @version: v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataTableParameter {
    /**
     * 请求服务器端次数
     * 绘制计数器。这个是用来确保Ajax从服务器返回的是对应的（Ajax是异步的，因此返回的顺序是不确定的）。 要求在服务器接收到此参数后再返回
     */
    private int sEcho;
    /**
     * 列数
     */
    private int iColumns;
    /**
     * 列名称
     */
    private String sColumns;
    /**
     * 真实记录，第一条为0, 第一条数据的起始位置
     */
    private int iDisplayStart;
    /**
     * 每页显示的记录数
     */
    private int iDisplayLength;
    /**
     * 全局的搜索条件，条件会应用到每一列（ searchable需要设置为 true ）
     */
    private String search;
    /**
     * 列的Name列表
     */
    private List<String> mDataProps;
    /**
     * 列对应是否能排序
     */
    private List<Boolean> bSortables;
    /**
     * 排序列的编号
     */
    private List<Integer> iSortCols;
    /**
     * 排序列的名称
     */
    private List<String> iSortColsName;
    /**
     * 排布列排序形式 Asc/Desc
     */
    private List<String> sSortDirs;
    /**
     * 一共有几列排序
     */
    private int iSortingCols;
}
