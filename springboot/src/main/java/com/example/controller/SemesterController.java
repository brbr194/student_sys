package com.example.controller;

import com.example.common.Result;
import com.example.entity.Semester;
import com.example.service.SemesterService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/semester")
public class SemesterController {

    @Resource
    private SemesterService semesterService;
    /**
     * 查寻所有年级列表
     */
    @GetMapping("/all")
    public Result findAll(){
        List<Semester> all = semesterService.findAll();
        return Result.success(all);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Semester semester){
        PageInfo<Semester> studentPageInfo = semesterService.selectPage(pageNum, pageSize,semester);
        return Result.success(studentPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Semester semester){
        semesterService.add(semester);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Semester semester){
        semesterService.updateById(semester);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        semesterService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Semester semester = semesterService.findById(id);
        return Result.success(semester);
    }
}
