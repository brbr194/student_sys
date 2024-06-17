package com.example.service;

import com.example.entity.Grade;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeService {
    List<Grade> findAll();

    PageInfo<Grade> selectPage(Integer pageNum, Integer pageSize, Grade grade);

    void add(Grade grade);

    void updateById(Grade grade);

    void deleteById(Integer id);

    Grade findById(Integer id);
}
