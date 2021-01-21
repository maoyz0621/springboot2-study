/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.i18n.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.springboot2.common.data.Result;
import com.myz.springboot2.common.exception.BusinessException;
import com.myz.springboot2.common.utils.ValidatorUtils;
import com.myz.springboot2.i18n.config.I18nMessageUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author maoyz0621 on 2020/9/30
 * @version v1.0
 */
@RestController
@RequestMapping("/i18n")
public class I18nController {

    @GetMapping("/get")
    public String a() {
        String val = I18nMessageUtil.getMessage("user.name", LocaleContextHolder.getLocale());
        return val;
    }

    @GetMapping("/except")
    public String except(String val) {
        if ("1".equals(val)) {
            throw new BusinessException("{except.0.0}");
        } else if ("2".equals(val)) {
            throw new BusinessException("except.0.0");
        }
        return "1";
    }

    /**
     * 引发BindException
     */
    @RequestMapping(value = "/validated/save1", method = RequestMethod.POST)
    public Result saveCustomerPageValidated1(@Validated CustomerDto model) {
        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(model.toString());
        return okResult;
    }

    /**
     * 引发MethodArgumentNotValidException
     */
    @RequestMapping(value = "/validated/save2", method = RequestMethod.POST)
    public Result saveCustomerPageValidated2(@RequestBody @Validated CustomerDto model) {
        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(model.toString());
        return okResult;
    }


    /**
     * 引发MethodArgumentNotValidException
     */
    @RequestMapping(value = "/validated/save3", method = RequestMethod.POST)
    public Result<CustomerDto> saveCustomerPageValidated3(@RequestBody CustomerDto model) throws JsonProcessingException {
        Result<CustomerDto> validator = ValidatorUtils.validator(model);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(validator);
        System.out.println(s);
        return validator;
    }
}