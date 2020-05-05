/**
 * Copyright 2019 Inc.
 **/
package com.myz.log.example.controller;

import com.myz.log.example.common.QueryResult;
import com.myz.log.example.common.Result;
import com.myz.log.example.common.ResultGenerator;
import com.myz.log.example.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maoyz0621 on 19-6-12
 * @version: v1.0
 */
@Api(value = "使用log-aspect记录日志信息")
@RestController
public class LoggerController {

    @ApiOperation(value = "", notes = "")
    @GetMapping("/index")
    public QueryResult index(@RequestParam String name) {
        return QueryResult.success(name);
    }

    @GetMapping("/index1")
    public QueryResult index(@RequestParam String name, HttpServletRequest request) {
        return QueryResult.success(name);
    }

    @GetMapping("/getBean")
    public QueryResult index(UserVo userVo) {
        return QueryResult.success(userVo);
    }

    @PostMapping("/post")
    public Result postParam(UserVo userVo) {
        return ResultGenerator.genSuccessResult(userVo);
    }

    @PostMapping("/postJson")
    public Result postBody(@RequestBody UserVo userVo) {
        return ResultGenerator.genSuccessResult(userVo);
    }

}
