package com.myz.springboot2.mybatis.page.model.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by maoyz on 17-9-20.
 */
@Data
public class Role {
    private Integer roleId;
    private String roleName;
    private String description;
    //一对多关系(collection)
    private List<User> users;
}
