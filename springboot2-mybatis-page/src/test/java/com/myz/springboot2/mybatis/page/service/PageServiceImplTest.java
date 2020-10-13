package com.myz.springboot2.mybatis.page.service;

import com.myz.springboot2.mybatis.page.PageApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author maoyz0621 on 2020/9/23
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PageApplication.class)
public class PageServiceImplTest {

    @Resource
    IPageService pageService;

    @Test
    void selectAllPage() throws SQLException {

        pageService.selectAllPage();
    }
}