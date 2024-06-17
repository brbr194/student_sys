package com.example.service;

import com.example.entity.Semester;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SemesterService {
    List<Semester> findAll();

    PageInfo<Semester> selectPage(Integer pageNum, Integer pageSize, Semester semester);

    void add(Semester semester);

    void updateById(Semester semester);

    void deleteById(Integer id);

    Semester findById(Integer id);
}
