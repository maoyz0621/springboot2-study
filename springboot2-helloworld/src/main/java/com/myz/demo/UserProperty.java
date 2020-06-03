package com.myz.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件属性@ConfigurationProperties
 *
 * @author maoyz on 18-3-10.
 */
@Component
@ConfigurationProperties(prefix = "muser")
public class UserProperty {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
