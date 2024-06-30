package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="majors")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="major")
    private String major;

    /**
     * created_at
     */
    @Column(name="created_time")
    private String createdTime;

    /**
     * updated_at
     */
    @Column(name="updated_time")
    private String updatedTime;



}
