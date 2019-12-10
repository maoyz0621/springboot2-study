package com.myz.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author maoyz
 */
@RequestMapping("/home")
@RestController
public class MyController {

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;

    @Value("${context}")
    private String context;

    @Resource
    private UserProperty userProperty;

    /**
     * 属性测试
     *
     * @return
     */
    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public String name() {
        return name;
    }

    /**
     * 属性测试
     *
     * @return
     */
    @GetMapping(value = "/age")
    public String age() {
        return age.toString();
    }

    /**
     * 属性测试
     *
     * @return
     */
    @RequestMapping(value = "/context", method = RequestMethod.GET)
    public String context() {
        return context;
    }

    /**
     * 配置文件属性测试
     *
     * @return
     */
    @RequestMapping(value = "/userPropertyTest", method = RequestMethod.GET)
    public String userPropertyTest() {
        return userProperty.getName();
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "nihao";
    }

    @RequestMapping(value = "/index1")
    public String error() {
        int i = 1 / 0;
        return "nihao";
    }

}
