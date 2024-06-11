package com.example.controller;

import com.example.common.Result;

import com.example.entity.Student;
import com.example.service.StudentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description student
 * @author BEJSON
 * @date 2024-06-11
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody Student student) {
        Student dbStudent = studentService.login(student);
        return Result.success(dbStudent);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Student student){
        PageInfo<Student> studentPageInfo = studentService.selectPage(pageNum, pageSize,student);
        return Result.success(studentPageInfo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Student student){
        studentService.add(student);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Student student){
        studentService.updateById(student);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        studentService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable Integer id){
        Student student = studentService.findById(id);
        return Result.success(student);
    }


}