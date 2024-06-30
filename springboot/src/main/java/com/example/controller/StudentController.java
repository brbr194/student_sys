package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Student;
import com.example.service.StudentService;
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
 * @author BEJSON
 * @description student
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
                             Student student) {
        PageInfo<Student> studentPageInfo = studentService.selectPage(pageNum, pageSize, student);
        return Result.success(studentPageInfo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
        studentService.add(student);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Student student) {
        studentService.updateById(student);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        studentService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable Integer id) {
        Student student = studentService.findById(id);
        return Result.success(student);
    }


    @GetMapping("/export")
    public Result exp(HttpServletResponse response) throws IOException {
        // 1. 从数据库中查询出所有数据
        List<Student> studentList = studentService.findAll();
        if (studentList.isEmpty()) {
            throw new RuntimeException("无数据，不可导出！");
        }
        // 2. 定义一个 List，存储处理之后的数据，用于塞到 list 里
        List<Map<String, Object>> list = new ArrayList<>();
        // 3. 定义Map<key,value> 出来，遍历每一条数据，然后封装到 Map<key,value> 里，把这个 map 塞到 list 里
        for (Student s : studentList) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("学生学号", s.getStudentNumber());
            row.put("学生密码", s.getPassword());
            row.put("学生姓名", s.getName());
            row.put("学生年龄", s.getAge());
            row.put("学生性别", s.getGender());
            row.put("学生年级", s.getGrade());
            row.put("学生学院", s.getDepartment());
            row.put("学生专业", s.getMajor());
            row.put("学生邮箱", s.getEmail());
            row.put("学生电话", s.getPhoneNumber());

            list.add(row);
        }
        // 4. 创建一个 ExcelWriter，把 list 数据用这个writer写出来（生成出来）
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.write(list, true);
        // 5. 把这个 excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=StudentList.xlsx");
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(System.out);
        return Result.success();
    }


    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        List<Student> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Student.class);
        int duplicateNum = 0;
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Student student : infoList) {

                Student student1 = studentService.findByStudentNumber(student.getStudentNumber());
                if (student1 != null) {
                    duplicateNum++;
                } else {
                    studentService.add(student);
                }


            }
        }
        String err = "";
        if(duplicateNum == infoList.size()){
            return Result.error("无法导入数据，原因：所有学生学号均已存在！");
        }else if(duplicateNum > 0){
            err = "成功插入"+ (infoList.size()-duplicateNum) +"条数据，有" + duplicateNum + "条数据未插入，原因是部分学生学号已存在！";
        }
        return Result.success(err);
    }

}