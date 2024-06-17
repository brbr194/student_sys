package com.example.service;

import com.example.entity.Department;
import com.example.entity.Grade;
import com.example.entity.Student;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    List<Department> findAll();

    void deleteById(Integer id);

    Department findById(Integer id);

    void add(Department department);

    PageInfo<Department> selectPage(Integer pageNum, Integer pageSize, Department department);

    void updateById(Department department);
}
