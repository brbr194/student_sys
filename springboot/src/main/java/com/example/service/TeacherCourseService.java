package com.example.service;

import com.example.entity.TeacherCourse;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface TeacherCourseService {
    void add(TeacherCourse teacherCourse);

    PageInfo<TeacherCourse> selectPage(Integer pageNum, Integer pageSize, TeacherCourse teacherCourse);

    void deleteById(Integer id);
}
