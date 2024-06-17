package com.example.mapper;

import com.example.entity.StudentCourse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentCourseMapper {

    @Delete("delete from student_course where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into student_course (course_name, course_no, tc_id, student_id, semester, teacher_name) " +
            "VALUES (#{courseName},#{courseNo},#{tcId},#{studentId},#{semester},#{teacherName})")
    void insert(StudentCourse studentCourse);

    @Select("select * from student_course where student_id = #{studentId} and tc_id = #{tcId}")
    StudentCourse selectByCondition(StudentCourse studentCourse);

    List<StudentCourse> selectAll(StudentCourse studentCourse);
}
