/**
 * Copyright 2021 Inc.
 **/
package com.myz.encrypt.web;


import com.myz.encrypt.common.Result;
import com.myz.encrypt.config.annotation.EncryptResponse;
import com.myz.encrypt.entity.ReqParam;
import com.myz.encrypt.entity.RespParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/encrypt")
public class EncryptController {

    @RequestMapping(value = "/emp/list", method = RequestMethod.POST)
    public Result<RespParam> list(@RequestBody ReqParam data) throws IOException {
        log.info("data={}", data);
        RespParam respParam = new RespParam()
                .setAge(12).setUsername("haha");
        return Result.of(respParam);
    }

    /**
     * 返回值进行加密操作
     *
     * @param data
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/emp/encrypt", method = RequestMethod.POST)
    @EncryptResponse
    public Result<RespParam> encrypt(@RequestBody ReqParam data) throws IOException {
        log.info("data={}", data);
        RespParam respParam = new RespParam()
                .setAge(12)
                .setUsername("haha")
                .setPhone(151000L)
                .setParent(new RespParam("parent", 50))
                .setChild(Arrays.asList(new RespParam("child1", 12), new RespParam("child2", 14, 130130L)));
        return Result.of(respParam);
    }
}