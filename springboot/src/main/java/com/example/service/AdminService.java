package com.example.service;

import com.example.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    /**
     * 用户登录
     * @param admin
     * @return
     */
    public Admin login(Admin admin);

    /**
     * 分页查询
     * @return
     */
    public PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize,Admin admin);

    /**
     * 添加信息
     * @param admin
     */
    void add(Admin admin);

    /**
     * 更新
     * @param admin
     */
    void updateById(Admin admin);

    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);
}
