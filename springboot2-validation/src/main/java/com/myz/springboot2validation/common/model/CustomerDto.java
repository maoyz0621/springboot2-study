/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common.model;

import com.myz.springboot2validation.common.annotation.PhoneValidation;
import com.myz.springboot2validation.common.groups.Insert;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.Date;

/**
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@Setter
@Getter
@ToString
public class CustomerDto {

    @Size(min = 2, max = 20)
    private String username;

    @NotNull
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
     * 自定义校验
     */
    @PhoneValidation
    private String phone;

    /**
     * 枚举类型
     */
    @NotNull(groups = {Default.class, Insert.class}, message = "性别不能为空")
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
