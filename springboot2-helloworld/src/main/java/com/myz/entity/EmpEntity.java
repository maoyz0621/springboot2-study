package com.myz.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author maoyz on 18-1-4.
 */
@Table(name = "emp")
@Entity
public class EmpEntity {

    @Id
    @GeneratedValue
    @Column(name = "ACCT_ID", length = 18)
    private String id;

    @Column(name = "USERNAME", length = 25)
    @NotEmpty
    private String username;

    @Max(value = 20, message = "超过最大值了...")
    @Column(name = "AGE")
    @NotNull
    private Integer age;

    @Column(name = "POSITION", length = 255)
    private String position;

    @Column(name = "SALARY")
    private Double salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "START_DATE")
    private Date startDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "EmpEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }
}
