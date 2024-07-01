package com.example.mapper;

import com.example.entity.Log;
import com.example.entity.Major;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogMapper {

    @Delete("delete from log where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from log where id = #{id}")
    Log findById(Integer id);


        List<Log> selectAll(Log log);//

    @Insert("insert into log (user_id, contest , operator_name, ip) " +
            "VALUES (#{userId}, #{contest}, #{operatorName}, #{ip})")
    void insert(Log log);

    @Select("select * from log ")
    List<Log> findAll();

}

