/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.jackson.controller;

import com.myz.springboot2.jackson.domain.UserBean;
import com.myz.springboot2.jackson.domain.UserRBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author maoyz0621 on 2020/10/30
 * @version v1.0
 */
@RestController
@RequestMapping("/jackson")
@Slf4j
public class JacksonController {

    @GetMapping("/get")
    public UserRBean get(UserBean userBean) {
        log.info("get {}", userBean);
        UserRBean userRBean = new UserRBean();
        userRBean.setDate(userBean.getDate());
        userRBean.setDateTime(userBean.getDateTime());
        return userRBean;
    }

    @PostMapping("/post")
    public UserRBean post(UserBean userBean) {
        log.info("post {}", userBean);
        UserRBean userRBean = new UserRBean();
        return userRBean;
    }

    @PostMapping("/postJson")
    public UserRBean postJson(@RequestBody UserBean userBean) {
        log.info("postJson {}", userBean);
        UserRBean userRBean = new UserRBean();
        return userRBean;
    }
}