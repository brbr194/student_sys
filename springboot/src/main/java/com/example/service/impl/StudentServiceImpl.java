package com.example.service.impl;

import com.example.entity.Admin;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import com.example.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;


    @Override
    public Student findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void updateById(Student student) {

    }

    @Override
    public void add(Student student) {
        Student dbstudent = studentMapper.findByStudentNumber(student.getStudentNumber());
        if(dbstudent != null){
            throw new CustomException("学号已存在，请更换！");
        }

        studentMapper.insert(student);
    }

    @Override
    public PageInfo<Student> selectPage(Integer pageNum, Integer pageSize, Student student) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> admins = studentMapper.selectAll(student);
        return PageInfo.of(admins);
    }

    @Override
    public Student login(Student student) {
        return null;
    }
}
