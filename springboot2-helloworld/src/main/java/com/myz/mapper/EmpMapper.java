package com.myz.mapper;

import com.example.entity.EmpEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 集成mybatis
 *   作为一个映射接口@Mapper
 * @author maoyz on 18-1-4.
 */
@Mapper
public interface EmpMapper {

    EmpEntity selectByName(String username);

    List<EmpEntity> selectAll();

    int insertEmp(EmpEntity entity);

    int del(Map param);

    int update(EmpEntity emp);

    int update(Map param);
}
