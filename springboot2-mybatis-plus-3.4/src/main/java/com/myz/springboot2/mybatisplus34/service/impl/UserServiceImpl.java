package com.myz.springboot2.mybatisplus34.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myz.springboot2.mybatisplus34.entity.UserEntity;
import com.myz.springboot2.mybatisplus34.mapper.UserMapper;
import com.myz.springboot2.mybatisplus34.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maoyz
 * @since 2021-12-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {


    @Override
    public boolean insertBatch(Collection<UserEntity> entityList) {
        return getBaseMapper().insertBatch(entityList);
    }
}
