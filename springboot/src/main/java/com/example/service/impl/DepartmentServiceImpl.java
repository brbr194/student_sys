package com.example.service.impl;

import com.example.entity.Department;
import com.example.entity.Grade;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.GradeMapper;
import com.example.mapper.StudentMapper;
import com.example.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private StudentMapper studentMapper;
    @Override
    public List<Department> findAll() {
        return departmentMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Department dbdept = departmentMapper.findById(id);
        List<Student> stuList = studentMapper.findByDept(dbdept.getDepartment());
        if(!stuList.isEmpty()){
            throw new CustomException("有学生正与此学院相关，不能删除！！！");
        }
        departmentMapper.deleteById(id);
    }

    @Override
    public Department findById(Integer id) {
        return departmentMapper.findById(id);
    }

    @Override
    public void add(Department department) {
        Department dbdepartment = departmentMapper.findByDepartmentName(department.getDepartment());
        if(dbdepartment != null){
            throw new CustomException("学院已存在！");
        }
        departmentMapper.insert(department);
    }

    @Override
    public PageInfo<Department> selectPage(Integer pageNum, Integer pageSize, Department department) {
        PageHelper.startPage(pageNum, pageSize);
        List<Department> deptList = departmentMapper.selectAll(department);
        return PageInfo.of(deptList);
    }

    @Override
    public void updateById(Department department) {
        Department dbdepartment = departmentMapper.findByDepartmentName(department.getDepartment());
        if(dbdepartment != null && !Objects.equals(dbdepartment.getId(), department.getId())){
            throw new CustomException("学院已存在！");
        }
        department.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        departmentMapper.update(department);
    }

}
