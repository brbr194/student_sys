package com.example.service.impl;

import com.example.entity.Course;
import com.example.entity.Semester;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.mapper.SemesterMapper;
import com.example.service.SemesterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SemesterServiceImpl implements SemesterService {

    @Resource
    private SemesterMapper semesterMapper;
    @Resource
    private CourseMapper courseMapper;
    @Override
    public List<Semester> findAll() {
        return semesterMapper.findAll();
    }



    @Override
    public PageInfo<Semester> selectPage(Integer pageNum, Integer pageSize, Semester semester) {
        PageHelper.startPage(pageNum, pageSize);
        List<Semester> semList = semesterMapper.selectAll(semester);
        return PageInfo.of(semList);
    }

    @Override
    public void add(Semester semester) {
        if(semester.getEndDate().equals(semester.getStartDate())){
            throw new CustomException("开始年份与结束年份不能相同！");
        }
        int start = Integer.parseInt(semester.getStartDate());
        int end = Integer.parseInt(semester.getEndDate());
        if(end - start != 1){
            throw new CustomException("开始年份与结束年份只能相差1年！");
        }
        semester.makeName();
        Semester dbsemester = semesterMapper.findByName(semester.getSemesterName());
        if(dbsemester != null){
            throw new CustomException("学期已存在！");
        }
        semester.makeName();
        semesterMapper.insert(semester);
    }

    @Override
    public void updateById(Semester semester) {
        if(semester.getEndDate().equals(semester.getStartDate())){
            throw new CustomException("开始年份与结束年份不能相同！");
        }
        int start = Integer.parseInt(semester.getStartDate());
        int end = Integer.parseInt(semester.getEndDate());
        if(end - start != 1){
            throw new CustomException("开始年份与结束年份只能相差1年！");
        }
        semester.makeName();
        Semester dbsemester = semesterMapper.findByName(semester.getSemesterName());
        if(dbsemester != null && !Objects.equals(dbsemester.getId(), semester.getId())){
            throw new CustomException("学期已存在！");
        }
        semester.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        semesterMapper.update(semester);
    }

    @Override
    public void deleteById(Integer id) {
        Semester dbsemester = semesterMapper.findById(id);
        List<Course> findBySemester = courseMapper.findBySemester(dbsemester.getSemesterName());
        if(!findBySemester.isEmpty()){
            throw  new CustomException("有课程与该学期相关联，不能删除！");
        }
        semesterMapper.deleteById(id);
    }

    @Override
    public Semester findById(Integer id) {
        return semesterMapper.findById(id);
    }
}
