package com.myz.springboot2.mybatis.page.model.entity;

import com.myz.springboot2.mybatis.page.config.annotation.EncryptDecryptClass;
import com.myz.springboot2.mybatis.page.config.annotation.EncryptDecryptField;
import lombok.Data;

/*
 * Created by maoyz on 17-9-12.
 */
@Data
@EncryptDecryptClass(filed = {"lastName", "email"})
public class User {

    private Long id;
    private String lastName;

    @EncryptDecryptField
    private String gender;

    private String email;
    //　多对一关系(association)
    private Role role;

    public User() {
    }
}
