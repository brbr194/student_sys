package com.example.service;

import com.example.entity.Student;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student findById(Integer id);

    Student findByStudentName(String studentName);

    void deleteById(Integer id);

    void updateById(Student student);

    void add(Student student);

    PageInfo<Student> selectPage(Integer pageNum, Integer pageSize, Student student);

    Student login(Student student);



    List<Student> findAll();


    Student findByStudentNumber(String studentNumber);
}
