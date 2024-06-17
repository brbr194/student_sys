package com.example.mapper;

import com.example.entity.Grade;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GradeMapper {

    @Select("select * from grades")
    List<Grade> findAll();

    @Select("select * from grades where grade like concat('%',#{grade}, '%')")
    List<Grade> selectAll(Grade grade);
    @Select("select * from grades where grade  = #{grade}")
    Grade findByGrade(String grade);
    @Insert("insert into grades (grade) values (#{grade})")
    void insert(Grade grade);

    @Delete("delete from grades where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from grades where id = #{id}")
    Grade findById(Integer id);
    @Update("update grades set grade = #{grade}, updated_time = #{updatedTime} where id = #{id}")
    void update(Grade grade);
}
