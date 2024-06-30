package com.example.service.impl;

import com.example.entity.Score;
import com.example.entity.StudentCourse;
import com.example.exception.CustomException;
import com.example.mapper.ScoreMapper;
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
    @Resource
    private ScoreMapper scoreMapper;

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
            throw new CustomException("你已选择过该课程，无需重复选择！");
        }
        int t_id = studentCourseMapper.findTeacherId(studentCourse);
        studentCourse.setTeacherId(t_id);
        if (studentCourse.getState() == null){
            studentCourse.setState("未打分");
        }
        studentCourseMapper.insert(studentCourse);
    }


    @Override
    public void deleteById(Integer id) {
        StudentCourse dbSC = studentCourseMapper.findById(id);
        Score tscore = new Score();
        tscore.setCourseId(dbSC.getCourseId());
        tscore.setStudentId(dbSC.getStudentId());
        Score dbscore = scoreMapper.selectByCondition(tscore);
        if(dbscore != null){
            throw new CustomException("该选课记录已经打分了，无法删除！");
        }
        studentCourseMapper.deleteById(id);
    }
}
