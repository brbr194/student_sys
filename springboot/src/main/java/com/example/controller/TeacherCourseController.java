package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.entity.TeacherCourse;
import com.example.service.CourseService;
import com.example.service.TeacherCourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/teacherCourse")
public class TeacherCourseController {

    @Resource
    private TeacherCourseService teacherCourseService;
    /**
     * 查寻所有年级列表
     */
//    @GetMapping("/all")
//    public Result findAll(){
//        List<Course> all = courseService.findAll();
//        return Result.success(all);
//    }
//
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             TeacherCourse teacherCourse){
        PageInfo<TeacherCourse> PageInfo = teacherCourseService.selectPage(pageNum, pageSize,teacherCourse);
        return Result.success(PageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody TeacherCourse teacherCourse){
        teacherCourseService.add(teacherCourse);
        return Result.success();
    }


    /**
     * 删除
     */
        @DeleteMapping("/delete/{id}")
        public Result delete(@PathVariable Integer id){
            teacherCourseService.deleteById(id);
        return Result.success();
}

}
