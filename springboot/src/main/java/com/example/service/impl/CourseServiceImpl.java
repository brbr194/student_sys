package com.example.service.impl;

import com.example.entity.Course;
import com.example.entity.StudentCourse;
import com.example.entity.Teacher;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.mapper.StudentCourseMapper;
import com.example.mapper.TeacherMapper;
import com.example.service.CourseService;
import com.example.service.TeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private TeacherMapper teacherMapper;



    @Resource
    private StudentCourseMapper studentCourseMapper;

    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }

    @Override
    public PageInfo<Course> selectPage(Integer pageNum, Integer pageSize, Course course) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> couList = courseMapper.selectAll(course);
        return PageInfo.of(couList);
    }

    @Override
    public void add(Course course) {
        Course dbcourse = courseMapper.selectByCondition(course);
        Course findByCno = courseMapper.findByCno(course.getCourseNo());
        Teacher dbteacher = teacherMapper.getTeacherNameById(course.getTeacherId());
        course.setTeacherName(dbteacher.getName());
        if(findByCno != null){
            throw new CustomException("课程号重复，请修改！");
        }
        if(dbcourse != null){
            throw new CustomException("已有相同信息的课程，请修改！");
        }

        courseMapper.insert(course);
    }



    @Override
    public void updateById(Course course) {
        Course findByCno = courseMapper.findByCno(course.getCourseNo());
        Course dbcourse = courseMapper.selectByCondition(course);
        if(findByCno != null && !Objects.equals(findByCno.getId(), course.getId())){
            throw new CustomException("课程号重复，请修改！");
        }
        if(dbcourse != null){
            if(!Objects.equals(dbcourse.getId(), course.getId())){
                throw new CustomException("已有相同信息的课程，请修改！");
            }

        }

        course.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        courseMapper.update(course);
    }

    @Override
    public void deleteById(Integer id) {
        List<StudentCourse> dbstudentCourses = studentCourseMapper.selectByCourseId(id);
        if(!dbstudentCourses.isEmpty()){
            throw new CustomException("有学生正在选修该课程，不能删除！");
        }
        courseMapper.deleteById(id);
    }

    @Override
    public Course findById(Integer id) {
        return courseMapper.findById(id);
    }
}
