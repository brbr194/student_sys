package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="student_course")
public class StudentCourse {
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

    @Column(name="tc_id")
    private String tcId;

    @Column(name="student_id")
    private String studentId;

    @Column(name="teacher_name")
    private String teacherName;

    /**
     * semester
     */
    @Column(name="semester")
    private String semester;

    @Transient
    private String studentName;

}
