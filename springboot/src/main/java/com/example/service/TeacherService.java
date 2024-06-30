package com.example.service;

import com.example.entity.Teacher;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    Teacher login(Teacher teacher);

    PageInfo<Teacher> selectPage(Integer pageNum, Integer pageSize, Teacher teacher);

    void add(Teacher teacher);

    void updateById(Teacher teacher);

    void deleteById(Integer id);

    Teacher findById(Integer id);

    List<Teacher> findAll();

}
