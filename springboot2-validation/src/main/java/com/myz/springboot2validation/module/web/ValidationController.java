/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.module.web;

import com.alibaba.fastjson.JSONObject;
import com.myz.springboot2validation.common.Result;
import com.myz.springboot2validation.common.model.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@RestController
public class ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(ValidationController.class);

    /**
     * 开启校验 @Validated,抛出BindException, 执行@ExceptionHandler
     */
    @RequestMapping(value = "/validated/save1", method = RequestMethod.POST)
    public Result saveCustomerPageValidated(@Validated CustomerDto model) {
        logger.debug("birthday = {}" + model.getBirthday());

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    /**
     * 开启校验 @Validated, 同时绑定BindingResult
     */
    @RequestMapping(value = "/validated/save2", method = RequestMethod.POST)
    public Result saveCustomerPageValidated(@Validated CustomerDto model, BindingResult bindingResult) {
        logger.debug("birthday = {}" + model.getBirthday());

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getFieldErrors().forEach((error) -> {
                sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(" ;");
            });
            logger.error("************ {} *************", sb);
            Result errorResult = new Result();
            errorResult.setCode(400);
            errorResult.setMessage(sb.toString());
            return errorResult;
        }

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    /**
     * 开启校验 @Valid,抛出BindException, 执行@ExceptionHandler
     */
    @RequestMapping(value = "/valid/save1", method = RequestMethod.POST)
    public Result saveCustomerPageValid(@Valid CustomerDto model) {
        logger.debug("birthday = {}" + model.getBirthday());

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    /**
     * 开启校验 @Valid,同时绑定BindingResult
     */
    @RequestMapping(value = "/valid/save2", method = RequestMethod.POST)
    public Result saveCustomerPageValid(@Valid CustomerDto model, BindingResult bindingResult) {
        logger.debug("birthday = {}" + model.getBirthday());

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getFieldErrors().forEach((error) -> {
                sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(" ;");
            });
            logger.error("************ {} *************", sb);
            Result errorResult = new Result();
            errorResult.setCode(400);
            errorResult.setMessage(sb.toString());
            return errorResult;
        }

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
