/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.util;

import com.myz.springboot2mybatis.common.page.MyPage;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装将model转化为dto类，实现数据层和传输层的解耦
 *
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
public class Model2DtoUtil {

    /**
     * 将 model 修改为 dto
     */
    public static <T, K> K model2Dto(T source, Class<K> clazz) {
        if(source == null){
            return null;
        }
        Assert.notNull(clazz, "cls can't be null!");

        try {
            K dto = clazz.newInstance();
            CommonBeanUtils.copyProperties(source, dto);
            return dto;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new BeanCreationException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new BeanCreationException(e.getMessage());
        }
    }

    /**
     * 将list中 model -> dto
     */
    public static <T, K>  List<K> model2Dto(List<T> sourceList, Class<K> clazz) {
        if(sourceList == null){
            return null;
        }
        Assert.notNull(clazz, "clazz can't be null!");

        // list
        List<K> dstList = new ArrayList<>();

        dealListModel2Dto(sourceList, clazz, dstList);
        return dstList;
    }

    /**
     * 将 MyPage<model> 修改为  MyPage<Dto>
     */
    public static <T, K> MyPage<K> model2Dto(MyPage<T> sourcePage, Class<K> clazz) {
        if (sourcePage == null) {
            return null;
        }

        Assert.notNull(clazz, "clazz can't be null!");

        MyPage<K> dtoPage = new MyPage<>();
        dtoPage.setTotalCount(sourcePage.getTotalCount());
        dtoPage.setApiId(sourcePage.getApiId());
        dtoPage.setMessage(sourcePage.getMessage());
        dtoPage.setCode(sourcePage.getCode());

        // list
        List<T> sourceList = sourcePage.getRows();
        List<K> dstList = new ArrayList<>();

        dealListModel2Dto(sourceList, clazz, dstList);
        dtoPage.setRows(dstList);
        return dtoPage;
    }

    /**
     * 将modelList -->  dtoList
     */
    private static <T, K> void dealListModel2Dto(List<T> sourceList, Class<K> clazz, List<K> dtoList) {
        for (T source : sourceList) {
            try {
                K dto = clazz.newInstance();
                CommonBeanUtils.copyProperties(source, dto);
                dtoList.add(dto);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new BeanCreationException(e.getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new BeanCreationException(e.getMessage());
            }
        }
    }

}
