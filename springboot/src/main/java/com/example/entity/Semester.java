package com.example.entity;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;

/**
 * @description semesters
 * @author BEJSON
 * @date 2024-06-14
 */
@Entity
@Data
@Table(name="semesters")
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
    * semester_name
    */
    @Column(name="semester_name")
    private String semesterName;

    /**
    * start_date
    */
    @Column(name="start_date")
    private String startDate;

    /**
    * end_date
    */
    @Column(name="end_date")
    private String endDate;

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

    @Column(name = "flag")
    private String flag;

    public Semester() {
    }

    public void makeName(){
        semesterName = startDate + "-" + endDate + "-" + flag;
    }
}