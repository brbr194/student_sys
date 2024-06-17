package com.example.mapper;

import com.example.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Delete("delete from teacher where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from teacher where id = #{id}")
    Teacher findById(Integer id);

    @Select("select * from teacher where teacher_number = #{teacherNumber}")
    Teacher findByTeacherNumber(String teacherNumber);

    @Insert("insert into teacher (teacher_number, name, department_id, email, phone, password) " +
            "VALUES (#{teacherNumber},#{name},#{departmentId},#{email},#{phone},#{password})")
    void insert(Teacher teacher);
    @Select("select * from teacher where name like concat('%',#{name}, '%') " +
            "and teacher_number like concat('%',#{teacherNumber}, '%') ")
    List<Teacher> selectAll(Teacher teacher);
    @Update("update teacher set teacher_number = #{teacherNumber}, name = #{name}, " +
            "email = #{email}, phone = #{phone}, updated_time = #{updatedTime}, department_id = #{departmentId} " +
            "where id = #{id}")
    void update(Teacher teacher);
}
