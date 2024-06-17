package com.example.controller;

import com.example.common.Result;
import com.example.entity.Department;
import com.example.entity.Grade;
import com.example.entity.Student;
import com.example.service.DepartmentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {


    @Resource
    private DepartmentService departmentService;
    @GetMapping("/all")
    public Result findAll(){
        List<Department> all = departmentService.findAll();
        return Result.success(all);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Department department){
        PageInfo<Department> studentPageInfo = departmentService.selectPage(pageNum, pageSize,department);
        return Result.success(studentPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Department department){
        departmentService.add(department);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Department department){
        departmentService.updateById(department);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        departmentService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Department department = departmentService.findById(id);
        return Result.success(department);
    }

}
