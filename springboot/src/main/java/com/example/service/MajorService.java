package com.example.service;

import com.example.entity.Major;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MajorService {
    List<Major> findAll();

    void deleteById(Integer id);

    Major findById(Integer id);

    void add(Major major);

    PageInfo<Major> selectPage(Integer pageNum, Integer pageSize, Major major);

    void updateById(Major major);
}
