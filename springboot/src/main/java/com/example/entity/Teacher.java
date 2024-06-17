package com.example.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Select;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="teacher")
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * teacher_number
     */
    @Column(name="teacher_number")
    private String teacherNumber;

    /**
     * password
     */
    @Column(name="password")
    private String password;


    /**
     * name
     */
    @Column(name="name")
    private String name;

    /**
     * department_id
     */
//    @Column(name="department")
//    private String department;

    @Column(name="department_id")
    private String departmentId;

    /**
     * email
     */
    @Column(name="email")
    private String email;

    /**
     * phone
     */
    @Column(name="phone")
    private String phone;

    /**
     * created_time
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

    public Teacher() {
    }
}
