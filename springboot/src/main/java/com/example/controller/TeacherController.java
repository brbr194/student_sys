package com.example.controller;

import com.example.common.LoginLog;
import com.example.common.LogoutLog;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Teacher;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description teacher
 * @author BEJSON
 * @date 2024-06-11
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    @LoginLog("教师登录")
    public Result login(@RequestBody Teacher teacher) {
        Teacher dbteacher = teacherService.login(teacher);
        return Result.success(dbteacher);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Teacher teacher){
        PageInfo<Teacher> teacherPageInfo = teacherService.selectPage(pageNum, pageSize,teacher);
        return Result.success(teacherPageInfo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Teacher teacher){
        teacherService.add(teacher);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        teacherService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable Integer id){
        Teacher teacher = teacherService.findById(id);
        return Result.success(teacher);
    }

    @GetMapping("/all")
    public Result findAll(){
        List<Teacher> all = teacherService.findAll();
        return Result.success(all);
    }

    @PostMapping("/logout")
    @LogoutLog("教师退出登录")
    public Result logout(@RequestBody Teacher teacher){
        Teacher dbteacher = teacherService.findById(teacher.getId());
        return Result.success(dbteacher);
    }

}