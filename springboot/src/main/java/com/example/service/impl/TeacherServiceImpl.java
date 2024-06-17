package com.example.service.impl;

import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.exception.CustomException;
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
        teacherMapper.deleteById(id);
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherMapper.findById(id);
    }
}
