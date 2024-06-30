package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * course_name
     */
    @Column(name="course_name")
    private String courseName;

    /**
     * course_no
     */
    @Column(name="course_no")
    private String courseNo;

    /**
     * credits
     */
    @Column(name="credits")
    private String credits;

    /**
     * semester
     */
    @Column(name="semester")
    private String semester;

    @Column(name="teacher_id")
    private Integer teacherId;

    @Column(name="teacher_name")
    private String teacherName;

    /**
     * created_time
     */
    @Column(name="created_time")
    private String createdTime;

    /**
     * updated_time
     */
    @Column(name="updated_time")
    private String updatedTime;

    public Course() {
    }

}
