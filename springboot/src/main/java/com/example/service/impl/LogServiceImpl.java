package com.example.service.impl;

import com.example.entity.Log;
import com.example.entity.Major;
import com.example.mapper.LogMapper;
import com.example.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    @Override
    public PageInfo<Log> selectPage(Integer pageNum, Integer pageSize, Log log) {
        PageHelper.startPage(pageNum, pageSize);
        List<Log> logList = logMapper.selectAll(log);
        return PageInfo.of(logList);
    }

    @Override
    public void add(Log log) {
        logMapper.insert(log);
    }



    @Override
    public void deleteById(Integer id) {
        logMapper.deleteById(id);
    }

    @Override
    public Log findById(Integer id) {
        return logMapper.findById(id);
    }
}
