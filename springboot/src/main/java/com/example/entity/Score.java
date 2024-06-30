package com.example.entity;

import cn.hutool.core.annotation.Alias;
import lombok.Cleanup;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
@Table(name="score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Alias("成绩记录ID")
    private Integer id;


    @Column(name= "course_id")
    private Integer courseId;

    @Column(name="student_id")
    private Integer studentId;

    @Alias("课程分数")
    @Column(name = "score")
    private Double score;

    @Alias("教师评语")
    @Column(name = "comment")
    private String comment;

    @Alias("学生反馈信息")
    @Column(name = "feedback")
    private String feedback;

    @Alias("课程开设学期")
    @Column(name="semester")
    private String semester;

    @Alias("成绩状态")
    @Column(name = "state")
    private String state;

    @Alias("课程名")
    @Transient
    private String courseName;

    @Alias("课程编号")
    @Transient
    private String courseNo;


    @Alias("选课学生姓名")
    @Transient
    private String studentName;

    @Alias("选课学生学号")
    @Transient
    private String studentNumber;

    @Alias("任课教师")
    @Transient
    private String teacherName;
    @Getter
    @Transient
    private Integer teacherId;
    @Transient
    private int flag;
    @Transient
    private int selectRule;

}
