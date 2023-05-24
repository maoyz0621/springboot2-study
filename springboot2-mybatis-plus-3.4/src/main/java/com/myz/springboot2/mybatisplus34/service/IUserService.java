package com.myz.springboot2.mybatisplus34.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myz.springboot2.mybatisplus34.entity.UserEntity;

import java.util.Collection;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maoyz
 * @since 2021-12-14
 */
public interface IUserService extends IService<UserEntity> {

    boolean insertBatch(Collection<UserEntity> entityList);

}
