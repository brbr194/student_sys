package com.example.entity;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
@Data
public class CourseAnalysis {
    private String courseId;

    private double max;

    private double min;

    private double avg;

    private double rate;


    private int allCnt;

    private Map<String, String> map;

    public void setAllCnt(int allCnt) {
        this.allCnt = allCnt;
    }

    private int[] scoreCnt;


    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setScoreCnt(int[] scoreCnt) {
        this.scoreCnt = scoreCnt;
    }
}
