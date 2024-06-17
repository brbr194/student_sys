package com.example.service.impl;

import com.example.entity.Admin;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import com.example.service.StudentService;
import com.example.utils.JwtTokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;


    @Override
    public Student findById(Integer id) {
        return studentMapper.findById(id);
    }

    @Override
    public Student findByStudentName(String studentName) {
        return studentMapper.findByStudentName(studentName);
    }

    @Override
    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }

    @Override
    public void updateById(Student student) {
        Student dbstudent = studentMapper.findByStudentNumber(student.getStudentNumber());
        if(dbstudent != null && !Objects.equals(dbstudent.getId(), student.getId())){
            throw new CustomException("学号已存在，请更换！");
        }
        student.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        studentMapper.update(student);
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
        List<Student> students = studentMapper.selectAll(student);
        return PageInfo.of(students);
    }

    @Override
    public Student login(Student student) {
       Student dbStudent = studentMapper.findByStudentNumber(student.getStudentNumber());
        if(dbStudent == null){
            throw new CustomException("账号或密码错误！");
        }
        if(!dbStudent.getPassword().equals(student.getPassword())){
            throw new CustomException("账号或密码错误！");
        }
        String token = JwtTokenUtils.genToken(dbStudent.getId().toString(), dbStudent.getPassword());
        dbStudent.setToken(token);
        return dbStudent;
    }
}
