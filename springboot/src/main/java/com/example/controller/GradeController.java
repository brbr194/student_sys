package com.example.controller;

import com.example.common.Result;
import com.example.entity.Grade;
import com.example.entity.Grade;
import com.example.service.GradeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Resource
    private GradeService gradeService;
    /**
     * 查寻所有年级列表
     */
    @GetMapping("/all")
    public Result findAll(){
        List<Grade> all = gradeService.findAll();
        return Result.success(all);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Grade grade){
        PageInfo<Grade> studentPageInfo = gradeService.selectPage(pageNum, pageSize,grade);
        return Result.success(studentPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Grade grade){
        gradeService.add(grade);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Grade grade){
        gradeService.updateById(grade);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        gradeService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Grade grade = gradeService.findById(id);
        return Result.success(grade);
    }
}
