package com.example.controller;

import com.example.common.LoginLog;
import com.example.common.LogoutLog;
import com.example.common.Result;
import com.example.entity.Admin;

import com.example.entity.Student;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    @LoginLog("管理员登录")
    public Result login(@RequestBody Admin admin) {
        Admin dbAdmin = adminService.login(admin);
        return Result.success(dbAdmin);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
        public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                              Admin admin){
        PageInfo<Admin> adminPageInfo = adminService.selectPage(pageNum, pageSize,admin);
        return Result.success(adminPageInfo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        adminService.updateById(admin);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable Integer id){
        Admin admin = adminService.findById(id);
        return Result.success(admin);
    }

    @PostMapping("/logout")
    @LogoutLog("管理员退出登录")
    public Result logout(@RequestBody Admin admin){
        Admin dbadmin = adminService.findById(admin.getId());
        return Result.success(dbadmin);
    }
}
