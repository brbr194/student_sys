package com.example.service;

import com.example.entity.Log;
import com.example.entity.Major;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogService {


    PageInfo<Log> selectPage(Integer pageNum, Integer pageSize, Log log);

    void add(Log log);


    void deleteById(Integer id);

    Log findById(Integer id);
}
