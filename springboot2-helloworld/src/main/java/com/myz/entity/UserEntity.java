package com.myz.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @author maoyz on 18-1-4.
 */
@Table(name = "user")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @NotEmpty
    private String username;

    @Column
    @NotNull
    private Integer age;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                '}';
    }
}
