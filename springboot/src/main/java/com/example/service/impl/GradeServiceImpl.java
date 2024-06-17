package com.example.service.impl;

import com.example.entity.Department;
import com.example.entity.Grade;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.GradeMapper;
import com.example.mapper.StudentMapper;
import com.example.service.GradeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class GradeServiceImpl implements GradeService {

    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private StudentMapper studentMapper;
    @Override
    public List<Grade> findAll() {
        return gradeMapper.findAll();
    }

    @Override
    public PageInfo<Grade> selectPage(Integer pageNum, Integer pageSize, Grade grade) {
        PageHelper.startPage(pageNum, pageSize);
        List<Grade> deptList = gradeMapper.selectAll(grade);
        return PageInfo.of(deptList);
    }

    @Override
    public void add(Grade grade) {
        Grade dbgrade = gradeMapper.findByGrade(grade.getGrade());
        if(dbgrade != null){
            throw new CustomException("年级已存在！");
        }
        gradeMapper.insert(grade);
    }

    @Override
    public void updateById(Grade grade) {
        Grade dbgrade = gradeMapper.findByGrade(grade.getGrade());
        if(dbgrade != null && !Objects.equals(dbgrade.getId(), grade.getId())){
            throw new CustomException("年级已存在！");
        }
        grade.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        gradeMapper.update(grade);
    }

    @Override
    public void deleteById(Integer id) {
        Grade dbgrade = gradeMapper.findById(id);
        List<Student> stuList = studentMapper.findByGrade(dbgrade.getGrade());
        if(!stuList.isEmpty()){
            throw new CustomException("有学生正与此年级相关，不能删除！！！");
        }
        gradeMapper.deleteById(id);
    }

    @Override
    public Grade findById(Integer id) {
        return gradeMapper.findById(id);
    }
}
