package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.LoginLog;
import com.example.common.LogoutLog;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Department;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.service.DepartmentService;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private DepartmentService departmentService;

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


    @GetMapping("/export")
    public Result exp(HttpServletResponse response) throws IOException {
        // 1. 从数据库中查询出所有数据
        List<Teacher> teacherList = teacherService.findAll();
        if (teacherList.isEmpty()) {
            throw new RuntimeException("无数据，不可导出！");
        }
        // 2. 定义一个 List，存储处理之后的数据，用于塞到 list 里
        List<Map<String, Object>> list = new ArrayList<>();
        // 3. 定义Map<key,value> 出来，遍历每一条数据，然后封装到 Map<key,value> 里，把这个 map 塞到 list 里
        for (Teacher t : teacherList) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("教师工号", t.getTeacherNumber());
            row.put("教师姓名", t.getName());
            row.put("教师密码", t.getPassword());
            Department department = departmentService.findById(t.getDepartmentId());
            row.put("教师学院", department.getDepartment());
            row.put("教师电话", t.getPhone());
            row.put("教师邮箱", t.getEmail());
            list.add(row);
        }
        // 4. 创建一个 ExcelWriter，把 list 数据用这个writer写出来（生成出来）
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.write(list, true);
        // 5. 把这个 excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=TeacherList.xlsx");
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(System.out);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        List<Teacher> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Teacher.class);
        int errorNum = 0;
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Teacher teacher : infoList) {

                Teacher dbteacher = teacherService.findByTeacherNumber(teacher.getTeacherNumber());
                Department dbdepartment = departmentService.findByDeptName(teacher.getDepartmentName());
                if (dbteacher != null || dbdepartment == null) {
                    errorNum++;
                } else {

                    teacher.setDepartmentId(dbdepartment.getId());
                    teacherService.add(teacher);
                }
            }
        }
        String err = "";
        if(errorNum == infoList.size()){
            return Result.error("无法导入数据，原因：所有教师工号均已存在或学院不存在！");
        }else if(errorNum > 0){
            err = "成功插入"+ (infoList.size()-errorNum) +"条数据，有" + errorNum + "条数据未插入，原因是部分教师工号已存在或学院不存在！";
        }
        return Result.success(err);
    }

}