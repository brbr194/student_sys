package com.example.mapper;

import com.example.entity.Department;
import com.example.entity.Major;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MajorMapper {
    @Select("select * from majors")
    List<Major> findAll();

    @Delete("delete from majors where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from majors where id = #{id}")
    Major findById(Integer id);

    @Select("select * from majors where major = #{name}")
    Major findByMajorName(String name);

    @Insert("insert into majors (major) values (#{major})")
    void insert(Major major);
    @Update("update majors set major = #{major}, updated_time = #{updatedTime} where id = #{id}")
    void update(Major major);

    @Select("select * from majors where major like concat('%',#{major}, '%')")
    List<Major> selectAll(Major major);
}
