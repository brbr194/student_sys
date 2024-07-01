package com.example.controller;

import com.example.common.Result;
import com.example.entity.Log;
import com.example.entity.Major;
import com.example.service.LogService;
import com.example.service.MajorService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {


    @Resource
    private LogService logService;


    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                            Log log){
        PageInfo<Log> logPageInfo = logService.selectPage(pageNum, pageSize,log);
        return Result.success(logPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Log log){
        logService.add(log);
        return Result.success();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        logService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Log log = logService.findById(id);
        return Result.success(log);
    }

}
