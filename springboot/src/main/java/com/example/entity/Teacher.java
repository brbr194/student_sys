package com.example.entity;

import cn.hutool.core.annotation.Alias;
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
    @Alias("教师工号")
    @Column(name="teacher_number")
    private String teacherNumber;

    /**
     * password
     */
    @Alias("教师密码")
    @Column(name="password")
    private String password;


    /**
     * name
     */
    @Alias("教师姓名")
    @Column(name="name")
    private String name;

    /**
     * department_id
     */

    @Column(name="department_id")
    private Integer departmentId;

   @Alias("教师学院")
    private String departmentName;

    /**
     * email
     */
    @Alias("教师邮箱")
    @Column(name="email")
    private String email;

    /**
     * phone
     */
    @Alias("教师电话")
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
