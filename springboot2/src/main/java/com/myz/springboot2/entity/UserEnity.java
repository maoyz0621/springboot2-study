/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author maoyz on 2018/8/27
 * @version: v1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
@Entity
public class UserEnity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String username;

    @Column
    @NotNull
    private Integer age;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

}
