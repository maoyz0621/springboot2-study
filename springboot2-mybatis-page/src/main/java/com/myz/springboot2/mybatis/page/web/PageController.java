/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.page.web;

import com.myz.springboot2.mybatis.page.service.IPageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author maoyz0621 on 2020/9/23
 * @version v1.0
 */
@RestController
public class PageController {
    @Resource
    IPageService pageService;

    @GetMapping("/a")
    public String a() throws SQLException {
        pageService.selectAllPage();
        return null;
    }
}