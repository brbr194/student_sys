package com.example.entity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "contest")
    private String contest;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "time")
    private String time;
    @Column(name = "operator_name")
    private String operatorName;
    @Column(name = "ip")
    private String ip;

    public Log( String contest, Integer userId, String time, String operatorName, String ip) {
        this.contest = contest;
        this.userId = userId;
        this.time = time;
        this.operatorName = operatorName;
        this.ip = ip;
    }
}