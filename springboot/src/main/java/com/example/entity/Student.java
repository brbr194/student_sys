package com.example.entity;

import cn.hutool.core.annotation.Alias;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @description student
 * @author BEJSON
 * @date 2024-06-11
 */
@Entity
@Data
@Table(name="student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Alias("学生ID")
    private Integer id;

    @Alias("学生学号")
    @Column(name= "student_number")
    private String studentNumber;

    /**
     * password
     */
    @Alias("学生密码")
    @Column(name="password")
    private String password;

    /**
     * student_name
     */
    @Alias("学生姓名")
    @Column(name= "name")
    private String name;

    /**
     * gender
     */
    @Alias("学生性别")
    @Column(name="gender")
    private String gender;

    /**
     * birth_date
     */
    @Alias("学生年龄")
    @Column(name="age")
    private int age;

    /**
     * grade
     */
    @Alias("学生年级")
    @Column(name="grade")
    private String grade;

    /**
     * department
     */
    @Alias("学生学院")
    @Column(name="department")
    private String department;

    /**
     * major
     */
    @Alias("学生专业")
    @Column(name="major")
    private String major;

    /**
     * email
     */
    @Alias("学生邮箱")
    @Column(name="email")
    private String email;

    /**
     * phone_number
     */
    @Alias("学生电话")
    @Column(name="phone_number")
    private String phoneNumber;

    /**
     * updated_at
     */
    @Alias("学生ID")
    @Column(name="updated_time")
    private String updatedTime;

    /**
     * created_at
     */
    @Column(name="created_time")
    private String createdTime;


    @Column(name="role")
    private String role;

    @Transient
    private String token;

    public Student() {
    }

}