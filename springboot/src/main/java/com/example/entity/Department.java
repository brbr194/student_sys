package com.example.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Select;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="department")
    private String department;

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
