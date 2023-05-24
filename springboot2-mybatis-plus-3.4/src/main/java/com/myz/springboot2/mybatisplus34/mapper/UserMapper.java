package com.myz.springboot2.mybatisplus34.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myz.springboot2.mybatisplus34.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maoyz
 * @since 2021-12-14
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 自定义批量插入
     * @param entityList
     * @return
     */
    boolean insertBatch(@Param("entityList") Collection<UserEntity> entityList);

}