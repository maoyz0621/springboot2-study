/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.i18n.web;

import com.myz.springboot2.common.data.Result;
import com.myz.springboot2.common.exception.BusinessException;
import com.myz.springboot2.i18n.config.I18nMessageUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            throw new BusinessException("except.0.0");
        } else if ("2".equals(val)) {
            throw new BusinessException("except.0.1");
        }
        return "1";
    }

    @RequestMapping(value = "/validated/save1", method = RequestMethod.POST)
    public Result saveCustomerPageValidated(@Validated CustomerDto model) {
        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(model.toString());
        return okResult;
    }
}