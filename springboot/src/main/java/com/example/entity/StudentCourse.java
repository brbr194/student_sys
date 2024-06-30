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

    @Column(name= "course_id")
    private Integer courseId;

    @Column(name="student_id")
    private Integer studentId;

    @Column(name="teacher_name")
    private String teacherName;

    /**
     * semester
     */
    @Column(name="semester")
    private String semester;

    @Column(name = "state")
    private String state;

    @Column(name = "teacher_id")
    private Integer teacherId;

    @Transient
    private String studentName;

}
