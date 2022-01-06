package com.myz.springboot2.mybatis.encrypt.domain.entity;

import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptClass;
import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptField;
import lombok.Data;

/*
 * Created by maoyz on 17-9-12.
 */
@Data
@CryptClass(filed = {"lastName", "email"})
public class User {

    private Long id;
    private String lastName;

    @CryptField
    private String gender;

    private String email;
    //　多对一关系(association)
    private Role role;

    public User() {
    }
}
