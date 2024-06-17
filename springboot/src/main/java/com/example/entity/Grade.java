package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="grade")
    private String grade;


    @Column(name="created_time")
    private String createdTime;

    /**
     * updated_at
     */
    @Column(name="updated_time")
    private String updatedTime;

}
