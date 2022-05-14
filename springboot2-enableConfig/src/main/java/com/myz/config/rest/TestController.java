/**
 * Copyright 2022 Inc.
 **/
package com.myz.config.rest;

import com.myz.config.enable.validate.ValidateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author maoyz0621 on 2022/5/14
 * @version v1.0
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    ValidateService validateService;

    @RequestMapping("/1")
    public String test() {
        validateService.valid();
        return "1";
    }
}