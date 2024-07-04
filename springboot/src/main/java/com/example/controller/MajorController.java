package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Department;
import com.example.entity.Major;
import com.example.entity.Teacher;
import com.example.service.DepartmentService;
import com.example.service.MajorService;
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

@RestController
@RequestMapping("/major")
public class MajorController {


    @Resource
    private MajorService majorService;
    @GetMapping("/all")
    public Result findAll(){
        List<Major> all = majorService.findAll();
        return Result.success(all);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Major major){
        PageInfo<Major> majorPageInfo = majorService.selectPage(pageNum, pageSize,major);
        return Result.success(majorPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Major major){
        majorService.add(major);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Major major){
        majorService.updateById(major);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        majorService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Major major = majorService.findById(id);
        return Result.success(major);
    }


    @GetMapping("/export")
    public Result exp(HttpServletResponse response) throws IOException {
        // 1. 从数据库中查询出所有数据
        List<Major> majorList = majorService.findAll();
        if (majorList.isEmpty()) {
            throw new RuntimeException("无数据，不可导出！");
        }
        // 2. 定义一个 List，存储处理之后的数据，用于塞到 list 里
        List<Map<String, Object>> list = new ArrayList<>();
        // 3. 定义Map<key,value> 出来，遍历每一条数据，然后封装到 Map<key,value> 里，把这个 map 塞到 list 里
        for (Major m : majorList) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("专业名称", m.getMajor());
            list.add(row);
        }
        // 4. 创建一个 ExcelWriter，把 list 数据用这个writer写出来（生成出来）
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.write(list, true);
        // 5. 把这个 excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=MajorList.xlsx");
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(System.out);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        List<Major> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Major.class);
        int errorNum = 0;
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Major major : infoList) {

                Major dbmajor = majorService.findByMajor(major.getMajor());
                if (dbmajor != null ) {
                    errorNum++;
                } else {
                    majorService.add(major);
                }
            }
        }
        String err = "";
        if(errorNum == infoList.size()){
            return Result.error("无法导入数据，原因：所有专业已存在！");
        }else if(errorNum > 0){
            err = "成功插入"+ (infoList.size()-errorNum) +"条数据，有" + errorNum + "条数据未插入，原因是部分专业已存在！";
        }
        return Result.success(err);
    }

}
