package com.example.service.impl;

import com.example.entity.Course;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.mapper.TeacherMapper;
import com.example.service.TeacherService;
import com.example.utils.JwtTokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TeacherServiceImpl  implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private CourseMapper courseMapper;
    @Override
    public Teacher login(Teacher teacher) {
        Teacher dbteacher = teacherMapper.findByTeacherNumber(teacher.getTeacherNumber());
        if(dbteacher == null){
            throw new CustomException("账号或密码错误！");
        }
        if(!dbteacher.getPassword().equals(teacher.getPassword())){
            throw new CustomException("账号或密码错误！");
        }
        String token = JwtTokenUtils.genToken(dbteacher.getId().toString(), dbteacher.getPassword());
        dbteacher.setToken(token);
        return dbteacher;
    }

    @Override
    public PageInfo<Teacher> selectPage(Integer pageNum, Integer pageSize, Teacher teacher) {
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> teachers = teacherMapper.selectAll(teacher);
        return PageInfo.of(teachers);
    }

    @Override
    public void add(Teacher teacher) {
        Teacher dbteacher = teacherMapper.findByTeacherNumber(teacher.getTeacherNumber());
        if(dbteacher != null){
            throw new CustomException("学号已存在，请更换！");
        }
        teacherMapper.insert(teacher);
    }

    @Override
    public void updateById(Teacher teacher) {
        Teacher dbteacher = teacherMapper.findByTeacherNumber(teacher.getTeacherNumber());
        if(dbteacher != null && !Objects.equals(dbteacher.getId(), teacher.getId())){
            throw new CustomException("学号已存在，请更换！");
        }
        teacher.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        teacherMapper.update(teacher);
    }

    @Override
    public void deleteById(Integer id) {
        List<Course> dbcourse = courseMapper.findByTeacherId(id);
        if(!dbcourse.isEmpty()){
            throw new CustomException("该老师正在教授课程，不能删除！");
        }
        teacherMapper.deleteById(id);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherMapper.findAll();
    }

    @Override
    public Teacher findByTeacherNumber(String teacherNumber) {
        return teacherMapper.findByTeacherNumber(teacherNumber);
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherMapper.findById(id);
    }
}
