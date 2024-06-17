package com.example.entity;

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
    private Integer id;


    @Column(name= "student_number")
    private String studentNumber;

    /**
     * password
     */
    @Column(name="password")
    private String password;

    /**
     * student_name
     */
    @Column(name= "name")
    private String name;

    /**
     * gender
     */
    @Column(name="gender")
    private String gender;

    /**
     * birth_date
     */
    @Column(name="age")
    private int age;

    /**
     * grade
     */
    @Column(name="grade")
    private String grade;

    /**
     * department
     */
    @Column(name="department")
    private String department;

    /**
     * major
     */
    @Column(name="major")
    private String major;

    /**
     * email
     */
    @Column(name="email")
    private String email;

    /**
     * phone_number
     */
    @Column(name="phone_number")
    private String phoneNumber;

    /**
     * updated_at
     */
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