package com.example.service.impl;

import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;

import com.example.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;
    @Override
    public Admin login(Admin admin) {
        Admin dbAdmin = adminMapper.findByUserName(admin.getUsername());
        if(dbAdmin == null){
            throw new CustomException("账号或密码错误！");
        }
        if(!admin.getPassword().equals(dbAdmin.getPassword())){
            throw new CustomException("账号或密码错误！");
        }
        return dbAdmin;
    }

    @Override
    public PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize, Admin admin) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminMapper.selectAll(admin);
        return PageInfo.of(admins);
    }

    @Override
    public void add(Admin admin) {
        Admin dbAdmin = adminMapper.findByUserName(admin.getUsername());
        if(dbAdmin != null){
            throw new CustomException("用户名已存在，请更换！");
        }
        adminMapper.insert(admin);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public void updateById(Admin admin) {
        admin.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        adminMapper.updateById(admin);
    }
}
