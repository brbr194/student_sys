package com.example.controller;

import com.example.common.Result;
import com.example.entity.Major;
import com.example.service.DepartmentService;
import com.example.service.MajorService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

}
