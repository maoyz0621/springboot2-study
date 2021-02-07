/**
 * Copyright 2021 Inc.
 **/
package com.myz.app;

import com.myz.springboot2.common.data.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@RestController("/encrypt")
public class EncryptController {

    @RequestMapping(value = "/emp/list", method = RequestMethod.GET)
    public Result list(String data) throws IOException {
       return null;
    }
}