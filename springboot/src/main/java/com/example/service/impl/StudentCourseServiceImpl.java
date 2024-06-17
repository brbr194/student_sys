package com.example.service.impl;

import com.example.entity.StudentCourse;
import com.example.entity.TeacherCourse;
import com.example.exception.CustomException;
import com.example.mapper.StudentCourseMapper;
import com.example.service.StudentCourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    @Resource
    private StudentCourseMapper studentCourseMapper;
    @Override
    public PageInfo<StudentCourse> selectPage(Integer pageNum, Integer pageSize, StudentCourse studentCourse) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentCourse> couList = studentCourseMapper.selectAll(studentCourse);
        return PageInfo.of(couList);
    }

    @Override
    public void add(StudentCourse studentCourse) {
        StudentCourse dbstudentCourse = studentCourseMapper.selectByCondition(studentCourse);
        if(dbstudentCourse != null ){
            throw  new CustomException("你已选择过该课程，无需重复选择！");
        }
        studentCourseMapper.insert(studentCourse);
    }

    @Override
    public void deleteById(Integer id) {
        studentCourseMapper.deleteById(id);
    }
}
