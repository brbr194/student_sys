package com.example.service;

import com.example.entity.CourseAnalysis;
import com.example.entity.Score;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ScoreService {
    void add(Score score);

    PageInfo<Score> selectPage(Integer pageNum, Integer pageSize, Score score);

    void update(Score score);

    void deleteById(Integer id);

    PageInfo<Score> selectSubmittalScore(Integer pageNum, Integer pageSize, Score score);

    CourseAnalysis analysisCourse(Integer id);

    List<Score> findAll();

    List<Score> adminExport();

    List<Score> teacherExport(Score Score);

    String upload(MultipartFile file) throws IOException;


    Score findById(Integer id);
}
