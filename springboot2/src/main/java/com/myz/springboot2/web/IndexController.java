/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author maoyz on 2018/8/21
 * @version: v1.0
 */
@RestController
@Api("首次使用Swagger整合Controller")
public class IndexController {

    @Value("${com.myz.param.desc}")
    private String desc;

    @Value("${com.myz.random.test2}")
    private String random;

    @ApiOperation(value = "首页信息", notes = "首页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "必传字段", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "用户名称", required = false, dataType = "String")
    })
    @RequestMapping(value = "/index/{id}", method = RequestMethod.GET)
    public String index(@PathVariable Integer id, @RequestBody(required = false) String name) {
        return "ID为:" + id + "用户:" + name + desc + "-->" + random;
    }
}
