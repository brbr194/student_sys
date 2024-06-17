package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="teacher_course")
public class TeacherCourse {
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


    @Column(name="course_id")
    private String courseId;

    @Column(name="teacher_id")
    private String teacherId;




    @Transient
    private String teacherName;


    /**
     * semester
     */
    @Column(name="semester")
    private String semester;

}
