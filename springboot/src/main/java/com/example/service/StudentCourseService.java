package com.example.service;

import com.example.entity.StudentCourse;
import com.example.entity.TeacherCourse;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface StudentCourseService {
    PageInfo<StudentCourse> selectPage(Integer pageNum, Integer pageSize, StudentCourse studentCourse);

    void add(StudentCourse studentCourse);

    void deleteById(Integer id);
}
