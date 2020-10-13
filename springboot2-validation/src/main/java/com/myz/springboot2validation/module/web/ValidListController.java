/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2validation.module.web;

import com.alibaba.fastjson.JSONObject;
import com.myz.springboot2validation.common.Result;
import com.myz.springboot2validation.common.ValidList;
import com.myz.springboot2validation.common.model.CustomerDto;
import com.myz.springboot2validation.common.model.ListRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author maoyz0621 on 2020/9/18
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/valid")
public class ValidListController {

    /**
     * List对象判断
     * 方法二：使用自定义的ValidList,无效果
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/valid/list")
    public Result list(@RequestBody @Valid ValidList<CustomerDto> model) {
        /**
         * [
         *     {
         *         "username": "mao",
         *         "age": 1,
         *         "email": "2333"
         *     }
         * ]
         */
        log.debug("model = {}", model);

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        return okResult;
    }

    /**
     * 方法三：
     * ConstraintViolationException
     * @param model
     * @return
     */
    @PostMapping(value = "/valid/list1")
    public Result list1(@RequestBody @Validated ListRequestParam<CustomerDto> model) {
        log.debug("model = {}", model);
        /**
         * {
         *     "list": [
         *         {
         *             "username": "mao",
         *             "age": 1,
         *             "email": "2333"
         *         }
         *     ]
         * }
         */

        Result okResult = new Result();
        okResult.setCode(200);
        okResult.setMessage(JSONObject.toJSON(model).toString());
        /**
         * {
         *     "code": 200001,
         *     "message": "list[0].email:不是一个合法的电子邮件地址;list[1].email:不是一个合法的电子邮件地址;list[1].birthday:不能为null;list[0].age:最小不能小于10;list[0].birthday:不能为null;list[1].age:最小不能小于10;list[1].gender:不能为null;list[0].gender:不能为null;",
         *     "content": null
         * }
         */
        return okResult;
    }
}