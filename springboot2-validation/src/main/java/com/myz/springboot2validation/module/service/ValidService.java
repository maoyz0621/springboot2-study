/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2validation.module.service;

import com.myz.springboot2validation.common.Result;
import com.myz.springboot2validation.common.groups.Insert;
import com.myz.springboot2validation.common.model.CustomerDto;
import com.myz.springboot2validation.utils.ValidatorUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 只有@Valid不起效果，必须配合@Validated
 *
 * @author maoyz0621 on 2022/4/8
 * @version v1.0
 */
@Validated
@Service
public class ValidService {

    public Result valid1(@Valid CustomerDto dto) {
        return null;
    }

    public Result valid2(CustomerDto dto) {
        Result validator = ValidatorUtils.validator(dto);
        if (!validator.isSuccess()) {
            return validator;
        }
        return null;
    }

    /**
     * 针对service，分组检验
     * 1、方法上@Validated(Update.class)
     * 2、参数上加@Valid
     *
     * @param dto
     * @return
     */
    @Validated(Insert.class)
    public Result validGroup(@Valid CustomerDto dto) {
        return null;
    }
}