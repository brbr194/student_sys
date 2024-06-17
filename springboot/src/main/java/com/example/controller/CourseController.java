package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.entity.Grade;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;
    /**
     * 查寻所有年级列表
     */
    @GetMapping("/all")
    public Result findAll(){
        List<Course> all = courseService.findAll();
        return Result.success(all);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Course course){
        PageInfo<Course> studentPageInfo = courseService.selectPage(pageNum, pageSize,course);
        return Result.success(studentPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Course course){
        courseService.add(course);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Course course){
        courseService.updateById(course);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        courseService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Course course = courseService.findById(id);
        return Result.success(course);
    }
}
