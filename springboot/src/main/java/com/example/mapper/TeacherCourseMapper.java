package com.example.mapper;

import com.example.entity.TeacherCourse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherCourseMapper {
    @Insert("insert into teacher_course (course_name, course_no, teacher_id, course_id, semester) " +
            "VALUES (#{courseName},#{courseNo},#{teacherId},#{courseId},#{semester})")
    void insert(TeacherCourse teacherCourse);

    @Select("select * from teacher_course where course_id = #{courseId} and teacher_id = #{teacherId}")
    TeacherCourse selectByCondition(TeacherCourse teacherCourse);

    /*@Select("select * from teacher_course where course_name like concat('%', #{courseName}, '%') " +
            "and course_no like concat('%', #{courseNo}, '%') " +
            "and semester like concat('%', #{semester}, '%')")*/
    List<TeacherCourse> selectAll(TeacherCourse teacherCourse);

    @Delete("delete from teacher_course where id = #{id}")
    void deleteById(Integer id);
}
