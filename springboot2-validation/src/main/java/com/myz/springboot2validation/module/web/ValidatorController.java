/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.module.web;

import com.myz.springboot2validation.common.Result;
import com.myz.springboot2validation.common.model.CustomerDto;
import com.myz.springboot2validation.module.service.ValidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author maoyz0621 on 19-1-11
 * @version v1.0
 */
@RestController
@RequestMapping("/validator")
public class ValidatorController {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);

    @Resource
    ValidService validService;

    /**
     * 自行校验不通过
     */
    @GetMapping(value = "/valid1")
    public Result valid1() {
        CustomerDto dto = new CustomerDto();
        dto.setUsername("a");
        dto.setAge(80);
        dto.setGender(CustomerDto.Gender.MALE);
        return validService.valid1(dto);
    }

    @GetMapping(value = "/valid2")
    public Result valid2() {
        CustomerDto dto = new CustomerDto();
        dto.setUsername("a");
        dto.setAge(80);
        // dto.setGender(CustomerDto.Gender.MALE);
        return validService.valid2(dto);
    }

    @GetMapping(value = "/validGroup")
    public Result validGroup() {
        logger.info("开始校验service中的分组校验");
        CustomerDto dto = new CustomerDto();
        dto.setUsername("a");
        dto.setAge(80);
        dto.setGender(CustomerDto.Gender.MALE);
        return validService.validGroup(dto);
    }

}