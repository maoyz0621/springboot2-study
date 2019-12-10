package com.myz.service;

import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author maoyz on 18-1-4.
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 事务管理
     */
    @Transactional(rollbackFor = Exception.class)
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(12);
        userEntity.setUsername("a");
        userRepository.save(userEntity);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setAge(13);
        userRepository.save(userEntity1);

    }
}
