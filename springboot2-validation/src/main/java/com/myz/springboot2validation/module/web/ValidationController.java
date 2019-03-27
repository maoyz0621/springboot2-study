/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.module.web;

import com.alibaba.fastjson.JSONObject;
import com.myz.springboot2validation.common.Result;
import com.myz.springboot2validation.common.model.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@RestController
public class ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(ValidationController.class);

    /**
     * 开启校验 @Validated
     */
    @RequestMapping(value = "/validation/save", method = RequestMethod.POST)
    public Result saveCustomerPage(@Validated CustomerDto model) {
        logger.debug("birthday = {}" + model.getBirthday());

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get() {
        int i = 1 / 0;
        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage("ok");
        return okResult;
    }
}
