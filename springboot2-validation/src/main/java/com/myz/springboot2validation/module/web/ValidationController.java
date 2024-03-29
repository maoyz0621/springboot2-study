/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.module.web;

import com.alibaba.fastjson.JSONObject;
import com.myz.springboot2validation.common.Result;
import com.myz.springboot2validation.common.model.CustomerDto;
import com.myz.springboot2validation.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author maoyz0621 on 19-1-11
 * @version v1.0
 */
@RestController
public class ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(ValidationController.class);

    /**
     * 开启校验 @Validated,抛出BindException, 执行@ExceptionHandler
     * 入参：json，异常类型：MethodArgumentNotValidException
     * 1. @RequestBody -> MethodArgumentNotValidException
     * 2. 无@RequestBody -> BindException
     */
    @RequestMapping(value = "/validated/save1", method = RequestMethod.POST)
    public Result saveCustomerPageValidated(/*@RequestBody*/ @Validated CustomerDto model) {
        logger.debug("birthday = {}", model.getBirthday());

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    /**
     * 开启校验 @Validated, 同时绑定BindingResult，不走ExceptionHandler
     */
    @RequestMapping(value = "/validated/save2", method = RequestMethod.POST)
    public Result saveCustomerPageValidated(@RequestBody @Validated CustomerDto model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return errorResult(bindingResult);
        }

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    /**
     * 开启校验 @Valid,抛出BindException, 执行@ExceptionHandler
     * 入参：form，异常类型：BindException
     * 1. @RequestBody -> MethodArgumentNotValidException
     * 2. 无@RequestBody -> BindException
     */
    @RequestMapping(value = "/valid/save1", method = RequestMethod.POST)
    public Result saveCustomerPageValid(@RequestBody @Valid CustomerDto model) {
        logger.debug("birthday = {}", model.getBirthday());

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    /**
     * 开启校验 @Valid,同时绑定BindingResult
     */
    @RequestMapping(value = "/valid/save2", method = RequestMethod.POST)
    public Result saveCustomerPageValid(@RequestBody @Valid CustomerDto model, BindingResult bindingResult) {
        logger.debug("birthday = {}", model.getBirthday());

        if (bindingResult.hasErrors()) {
            return errorResult(bindingResult);
        }

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }


    /**
     * 自行校验不通过
     */
    @GetMapping(value = "/valid")
    public Result valid() {
        CustomerDto dto = new CustomerDto();
        dto.setUsername("a");
        dto.setAge(80);
        dto.setGender(CustomerDto.Gender.MALE);
        Result validator = ValidatorUtils.validator(dto);
        if (!validator.isSuccess()) {
            return validator;
        }
        return null;
    }

    /**
     * 自行校验通过
     */
    @GetMapping(value = "/valid1")
    public Result valid1() {
        CustomerDto dto = new CustomerDto();
        dto.setUsername("aaa");
        dto.setAge(50);
        dto.setEmail("22222222@qq.com");
        dto.setGender(CustomerDto.Gender.MALE);
        dto.setBirthday(new Date());
        Result validator = ValidatorUtils.validator(dto);
        if (!validator.isSuccess()) {
            return validator;
        }
        return null;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get() {
        int i = 1 / 0;
        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage("ok");
        return okResult;
    }

    private Result errorResult(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors().forEach((error) -> {
            sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(" ;");
        });

        logger.error("{}", sb);
        Result errorResult = new Result();
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;
    }
}
