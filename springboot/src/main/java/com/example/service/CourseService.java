package com.example.service;

import com.example.entity.Course;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    List<Course> findAll();

    PageInfo<Course> selectPage(Integer pageNum, Integer pageSize, Course course);

    void add(Course course);

    void updateById(Course course);

    void deleteById(Integer id);

    Course findById(Integer id);
}
