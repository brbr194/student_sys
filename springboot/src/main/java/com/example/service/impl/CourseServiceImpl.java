package com.example.service.impl;

import com.example.entity.Course;
import com.example.entity.Semester;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.mapper.SemesterMapper;
import com.example.service.CourseService;
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
        List<Course> findByCourseName = courseMapper.findByName(course.getCourseName());
        List<Course> findBySemester = courseMapper.findBySemester(course.getSemester());
        Course findByCno = courseMapper.findByCno(course.getCourseNo());
        if(findByCno != null){
            throw new CustomException("课程号重复，请修改！");
        }
        if(findByCourseName != null){
            for(Course c: findByCourseName){
                if(c.getSemester().equals(course.getSemester())){
                        throw new CustomException("课程 "+course.getCourseName()+" 在学期 "+course.getSemester()+" 已存在，请修改！");
                }
            }
        }
        if(findBySemester != null){
            for(Course c: findBySemester){
                if(c.getCourseName().equals(course.getCourseName())){
                        throw new CustomException("学期 "+course.getSemester()+" 已有名为 "+course.getCourseName()+" 的课程，请修改！");
                }
            }
        }
        courseMapper.insert(course);
    }



    @Override
    public void updateById(Course course) {
        List<Course> findByCourseName = courseMapper.findByName(course.getCourseName());
        List<Course> findBySemester = courseMapper.findBySemester(course.getSemester());
        Course findByCno = courseMapper.findByCno(course.getCourseNo());
        if(findByCno != null && !Objects.equals(findByCno.getId(), course.getId())){
            throw new CustomException("课程号重复，请修改！");
        }
        if(findByCourseName != null){
            for(Course c: findByCourseName){
                if(c.getSemester().equals(course.getSemester())){
                    if(!Objects.equals(c.getId(), course.getId())){
                        throw new CustomException("课程 "+course.getCourseName()+" 在学期 "+course.getSemester()+" 已存在，请修改！");
                    }
                }
            }
        }
        if(findBySemester != null){
            for(Course c: findBySemester){
                if(c.getCourseName().equals(course.getCourseName())){
                    if(!Objects.equals(c.getId(), course.getId())){
                        throw new CustomException("学期 "+course.getSemester()+" 已有名为 "+course.getCourseName()+" 的课程，请修改！");
                    }
                }
            }
        }
        course.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        courseMapper.update(course);
    }

    @Override
    public void deleteById(Integer id) {
        courseMapper.deleteById(id);
    }

    @Override
    public Course findById(Integer id) {
        return courseMapper.findById(id);
    }
}
