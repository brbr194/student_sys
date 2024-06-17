package com.example.service.impl;

import com.example.entity.Course;
import com.example.entity.TeacherCourse;
import com.example.exception.CustomException;
import com.example.mapper.TeacherCourseMapper;
import com.example.service.TeacherCourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

    @Resource
    private TeacherCourseMapper teacherCourseMapper;
    @Override
    public void add(TeacherCourse teacherCourse) {
        TeacherCourse dbteacherCourse = teacherCourseMapper.selectByCondition(teacherCourse);
        if(dbteacherCourse != null){
            throw  new CustomException("您正在教授此课程，无需重复选择！");
        }
        teacherCourseMapper.insert(teacherCourse);
    }

    @Override
    public PageInfo<TeacherCourse> selectPage(Integer pageNum, Integer pageSize, TeacherCourse teacherCourse) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherCourse> couList = teacherCourseMapper.selectAll(teacherCourse);
        return PageInfo.of(couList);
    }

    @Override
    public void deleteById(Integer id) {
        teacherCourseMapper.deleteById(id);
    }
}
