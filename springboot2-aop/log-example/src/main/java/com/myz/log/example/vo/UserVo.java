/**
 * Copyright 2019 Inc.
 **/
package com.myz.log.example.vo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author maoyz0621 on 19-6-12
 * @version: v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Long userId;

    private String username;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
