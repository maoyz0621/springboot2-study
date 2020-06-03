/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.service;

import com.example.demo.ChargeRuleModel;
import com.example.entity.datatable.DataTableParameter;
import com.example.entity.datatable.ReceivedData;
import com.example.mapper.EmpMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz on 2018/7/20
 * @version: v1.0
 */
@Service
public class DataTableService {

    @Resource
    private EmpMapper empMapper;

    /**
     * DataTableParameter类型
     */
    public PageInfo<?> list(DataTableParameter dataTableParam) {
        pageSearch(dataTableParam);
        // 紧跟着的第一个select方法会被分页，contryMapper会被PageInterceptor截拦,截拦器会从threadlocal中取出分页信息，把分页信息加到sql语句中，实现了分页查旬
        List<?> empEntities = empMapper.selectAll();
        PageInfo<?> pageInfo = new PageInfo<>(empEntities);
        return pageInfo;
    }

    /**
     * Map类型
     */
    public PageInfo<?> list(Map dataMap) {
        pageSearch(dataMap);
        // 紧跟着的第一个select方法会被分页，contryMapper会被PageInterceptor截拦,截拦器会从threadlocal中取出分页信息，把分页信息加到sql语句中，实现了分页查旬
        List<?> empEntities = empMapper.selectAll();
        PageInfo<?> pageInfo = new PageInfo<>(empEntities);
        return pageInfo;
    }

    /**
     * ReceivedData类型
     */
    public PageInfo<?> list(ReceivedData receivedData) {
        pageSearch(receivedData);
        // 紧跟着的第一个select方法会被分页，contryMapper会被PageInterceptor截拦,截拦器会从threadlocal中取出分页信息，把分页信息加到sql语句中，实现了分页查旬
        List<?> empEntities = empMapper.selectAll();
        PageInfo<?> pageInfo = new PageInfo<>(empEntities);
        return pageInfo;
    }

    /**
     * 高级搜索
     */
    public List<ChargeRuleModel> advancedSearch(ChargeRuleModel model, DataTableParameter dataTableParam) {
        return null;
    }

    /**
     * 条件查询
     */
    public List<ChargeRuleModel> conditionSearch(ChargeRuleModel model, DataTableParameter dataTableParam) {
        return null;
    }

    /**
     * and条件查询
     */
    public List<ChargeRuleModel> conditionAndSearch(ChargeRuleModel model, DataTableParameter dataTableParam) {
        return null;
    }

    /**
     * or条件查询
     */
    public List<ChargeRuleModel> conditionOrSearch(ChargeRuleModel model, DataTableParameter dataTableParam) {
        return null;
    }

    /**
     * 排序
     */
    public List<ChargeRuleModel> sortSearch(ChargeRuleModel model, DataTableParameter dataTableParam) {
        return null;
    }

    /**
     * DataTableParameter分页查询
     */
    public void pageSearch(DataTableParameter data) {
        // 开始行数
        int start = data.getIDisplayStart();
        // 长度
        int length = data.getIDisplayLength();
        //
        int pageIndex = start / length + 1;
        // 设置分页信息保存到threadLocal中
        PageHelper.startPage(pageIndex, length, true);
    }

    /**
     * Map分页查询
     */
    public void pageSearch(Map dataMap) {
        // 页数
        int start = (int) dataMap.get("pageIndex");
        // 长度
        int length = (int) dataMap.get("pageSize");
        // 设置分页信息保存到threadLocal中
        PageHelper.startPage(start, length, true);
    }

    /**
     * ReceivedData分页查询
     */
    public void pageSearch(ReceivedData data) {
        // 开始行数
        int start = data.getStart();
        // 长度
        int length = data.getLength();
        int pageIndex = start / length + 1;
        // 设置分页信息保存到threadLocal中
        PageHelper.startPage(pageIndex, length, true);
    }

    /**
     * 字典类型转换
     */
    public List<ChargeRuleModel> code2Name(ChargeRuleModel model, DataTableParameter dataTableParam) {
        return null;
    }
}
