/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.jackson.controller;

import com.myz.springboot2.jackson.domain.UserBean;
import com.myz.springboot2.jackson.domain.UserFormBean;
import com.myz.springboot2.jackson.domain.UserRBean;
import com.myz.springboot2.jackson.domain.UserStdConverterBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    public UserFormBean post(UserFormBean userBean) {
        log.info("post {}", userBean);
        UserFormBean userRBean = new UserFormBean();
        userRBean.setDate(userBean.getDate());
        userRBean.setDateTime(userBean.getDateTime());
        return userRBean;
    }

    @PostMapping("/postJson")
    public UserBean postJson(@RequestBody UserBean userBean) {
        log.info("postJson {}", userBean);
        UserBean userRBean = new UserBean();
        userRBean.setDate(new Date());
        return userRBean;
    }

    /**
     * {"date":1630598688216,"dateTime":1630598688216,"dateToLong":"2021-08-02 11:11:12","dateTimeToLong":"2021-08-02 11:11:12"}
     */
    @PostMapping("/postJson1")
    public UserStdConverterBean postJson1(@RequestBody UserStdConverterBean userBean) {
        // UserStdConverterBean(date=Fri Sep 03 00:04:48 CST 2021, dateTime=Fri Sep 03 00:04:48 CST 2021,
        // dateToLong=1627873872000, dateTimeToLong=1627873872000)
        log.info("postJson {}", userBean);
        UserStdConverterBean bean = new UserStdConverterBean();
        bean.setDate(new Date());
        bean.setDateTime(new Date());
        bean.setDateToLong(System.currentTimeMillis());
        bean.setDateTimeToLong(System.currentTimeMillis());
        /**
         {
             "date": 1630682213145,
             "dateTime": 1630682213145,
             "dateToLong": 1630682213145,
             "dateTimeToLong": "2021-09-03 23:16:53"
         }
         */
        return bean;
    }
}