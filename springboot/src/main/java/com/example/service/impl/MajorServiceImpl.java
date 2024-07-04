package com.example.service.impl;

import com.example.entity.Major;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.MajorMapper;
import com.example.mapper.StudentMapper;
import com.example.service.DepartmentService;
import com.example.service.MajorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MajorServiceImpl implements MajorService {
    @Resource
    private MajorMapper majorMapper;
    @Resource
    private StudentMapper studentMapper;
    @Override
    public List<Major> findAll() {
        return majorMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Major dbmajor = majorMapper.findById(id);
        List<Student> stuList = studentMapper.findByMajor(dbmajor.getMajor());
        if(!stuList.isEmpty()){
            throw new CustomException("有学生正选择此专业有关，不能删除！！！");
        }
        majorMapper.deleteById(id);
    }

    @Override
    public Major findById(Integer id) {
        return majorMapper.findById(id);
    }

    @Override
    public void add(Major major) {
        Major dbmajor = majorMapper.findByMajorName(major.getMajor());
        if(dbmajor != null){
            throw new CustomException("专业已存在！");
        }
        majorMapper.insert(major);
    }

    @Override
    public PageInfo<Major> selectPage(Integer pageNum, Integer pageSize, Major major) {
        PageHelper.startPage(pageNum, pageSize);
        List<Major> majorList = majorMapper.selectAll(major);
        return PageInfo.of(majorList);
    }

    @Override
    public Major findByMajor(String major) {
        return majorMapper.findByMajorName(major);
    }

    @Override
    public void updateById(Major major) {
        Major dbmajor = majorMapper.findByMajorName(major.getMajor());
        if(dbmajor != null && !Objects.equals(dbmajor.getId(), major.getId())){
            throw new CustomException("专业已存在！");
        }
        major.setUpdatedTime(String.valueOf(LocalDateTime.now()));
        majorMapper.update(major);
    }

}
