package com.myz.log.example.controller;

import com.myz.log.example.LogApplicationTests;
import com.myz.log.example.vo.UserVo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

/**
 * MockMvcResultHandlers.print() 打印请求信息
 *
 * @author maoyz0621 on 19-11-25
 * @version: v1.0
 */
public class LoggerControllerTest extends LogApplicationTests {


    /**
     * get请求
     * param() 参数
     * contentType() 请求类型
     */
    @Test
    public void index() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/index")
                .param("name", "maoyz")
                .contentType(MediaType.ALL_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                // {"code":200,"message":"SUCCESS","data":"maoyz","page":null}
                .andExpect(MockMvcResultMatchers.jsonPath(".data").value("maoyz"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void index1() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/index1?name=maoyz"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // {"code":200,"message":"SUCCESS","data":"maoyz","page":null}
                .andExpect(MockMvcResultMatchers.jsonPath(".data").value("maoyz"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * get请求 bean接收
     */
    @Test
    public void index2() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/getBean")
                .param("userId", "2")
                .param("username", "maoyz")
                .contentType(MediaType.ALL_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                // {"code":200,"message":"SUCCESS","data":"maoyz","page":null}
                .andExpect(MockMvcResultMatchers.jsonPath(".data.username").value("maoyz"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * post请求 form表单提交
     */
    @Test
    public void postParam() throws Exception {
        String content = this.mockMvc.perform(MockMvcRequestBuilders.post("/post")
                //数据的格式
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("userId", "2")
                .param("username", "maoyz"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 返回参数类型
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath(".data.userId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".data.username").value("maoyz"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * POST请求, json参数
     * content() json请求参数
     */
    @Test
    public void postBody() throws Exception {
        String content = this.mockMvc.perform(MockMvcRequestBuilders.post("/postJson")
                // 请求格式, 必须, 不加,  415
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                // 请求param
                .content(new UserVo(1L, "maoyz").toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 返回参数类型
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath(".data.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".data.username").value("maoyz"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }
}