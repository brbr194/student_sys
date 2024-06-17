package com.example.mapper;

import com.example.entity.Semester;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SemesterMapper {

    @Select("select * from semesters")
    List<Semester> findAll();
    @Select("select * from semesters where semester_name like concat('%',#{semesterName}, '%')")
    List<Semester> selectAll(Semester semester);

    @Select("select * from semesters where semester_name = #{semesterName}")
    Semester findByName(String semesterName);

    @Insert("insert into semesters (semester_name, start_date, end_date, flag) values (#{semesterName}, #{startDate},#{endDate},#{flag})")
    void insert(Semester semester);

    @Select("select * from semesters where id = #{id}")
    Semester findById(Integer id);
    @Update("update semesters set " +
            "semester_name = #{semesterName}, " +
            "updated_time = #{updatedTime}," +
            "start_date = #{startDate}," +
            "end_date = #{endDate}," +
            "flag = #{flag} where id = #{id}")
    void update(Semester semester);

    @Delete("delete from semesters where id = #{id}")
    void deleteById(Integer id);
}
