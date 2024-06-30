package com.example.controller;

import com.example.common.Result;
import com.example.entity.StudentCourse;
import com.example.service.StudentCourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {

    @Resource
    private StudentCourseService studentCourseService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             StudentCourse studentCourse){
        PageInfo<StudentCourse> PageInfo = studentCourseService.selectPage(pageNum, pageSize,studentCourse);
        return Result.success(PageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody StudentCourse studentCourse){
        studentCourseService.add(studentCourse);
        return Result.success();
    }


    /**
     * 删除
     */
        @DeleteMapping("/delete/{id}")
        public Result delete(@PathVariable Integer id){
            studentCourseService.deleteById(id);
        return Result.success();
}

}
