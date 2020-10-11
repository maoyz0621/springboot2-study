/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common.model;

import com.myz.springboot2validation.common.annotation.PhoneValidation;
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
    @NotNull(groups = Save.class)
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

    /**
     * 保存的时候校验分组
     */
    public interface Save {
    }

    /**
     * 更新的时候校验分组
     */
    public interface Update {
    }
}
