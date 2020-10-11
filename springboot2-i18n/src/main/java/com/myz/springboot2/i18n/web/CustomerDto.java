/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2.i18n.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@Setter
@Getter
@ToString
public class CustomerDto {

    /**
     * message = ""
     * 返回指定内容
     */
    @NotNull(message = "username不能为空")
    @Size(min = 2, max = 20)
    private String username;

    /**
     * message = "{}"
     * 返回配置的国际化内容
     */
    @NotNull(message = "{CustomerDto.NotNull.age}")
    @Min(10)
    @Max(65)
    private Integer age;

    @Email
    @NotEmpty(message = "自定义错误信息说明")
    private String email;

    /**
     * 处理前台传递的时间问题,通过@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     * <p>
     * 时间必须为当前日期之前 @Past
     */
    @NotNull
    @Past(message = "出生日期必须是过去时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    /**
     * 枚举类型
     */
    @NotNull
    private Gender gender;

    public enum Gender {
        /**
         * 男
         */
        MALE,

        /**
         * 女
         */
        FEMALE
    }
}
