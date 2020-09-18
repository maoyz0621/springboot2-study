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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author maoyz0621 on 19-1-11
 * @version v1.0
 */
@RestController
@Validated
@RequestMapping("/validated")
public class ValidatedController {

    private static final Logger logger = LoggerFactory.getLogger(ValidatedController.class);

    /**
     * List对象判断
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody @Valid List<CustomerDto> model) {
        logger.debug("model = {}", model);

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
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
