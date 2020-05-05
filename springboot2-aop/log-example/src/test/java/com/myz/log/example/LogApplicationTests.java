package com.myz.log.example;

import com.myz.log.example.controller.LoggerController;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 进行Mock测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LogApplication.class})
@AutoConfigureMockMvc
@WebAppConfiguration
public class LogApplicationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        // 方法1
        // this.mockMvc = MockMvcBuilders.standaloneSetup(LoggerController.class).build();

        // 方法2
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }


}
