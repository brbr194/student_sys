package com.example.mapper;


import com.example.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Delete("delete from courses where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from courses where id = #{id}")
    Course findById(Integer id);
    @Select("select * from courses")
    List<Course> findAll();

    @Select("select * from courses where course_name = #{courseName}")
    List<Course> findByName(String courseName);
    @Insert("insert into courses (course_no, course_name, credits, semester,teacher_id, teacher_name) " +
            "values (#{courseNo},#{courseName},#{credits},#{semester}, #{teacherId}, #{teacherName})")
    void insert(Course course);
    @Select("select * from courses where semester = #{semester}")
    List<Course> findBySemester(String semester);
    @Update("update courses set course_no = #{courseNo}," +
            "course_name = #{courseName}," +
            "updated_time = #{updatedTime}," +
            "credits = #{credits}," +
            "semester = #{semester}," +
            "teacher_name = #{teacherName}," +
            "teacher_id = #{teacherId} where id = #{id}")
    void update(Course course);
    @Select("select * from courses where course_no = #{courseNo}")
    Course findByCno(String courseNo);

   /* @Select("select * from courses where course_no like concat('%',#{courseNo},'%') " +
            "and course_name like concat('%',#{courseName}, '%')" +
            "and credits like concat('%',#{credits}, '%')" +
            "and semester like concat('%',#{semester}, '%')")*/
    List<Course> selectAll(Course course);

    Course selectByCondition(Course course);

    @Select("select * from courses where teacher_id = #{id}")
    List<Course> findByTeacherId(Integer id);
}
