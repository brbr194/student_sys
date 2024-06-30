package com.example.mapper;

import com.example.entity.Score;
import com.example.entity.StudentCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentCourseMapper {

    @Delete("delete from student_course where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into student_course (course_name, course_no, course_id, student_id, semester, teacher_name,teacher_id, state) " +
            "VALUES (#{courseName},#{courseNo},#{courseId},#{studentId},#{semester},#{teacherName},#{teacherId}, #{state})")
    void insert(StudentCourse studentCourse);

    @Select("select * from student_course where student_id = #{studentId} and course_id = #{courseId}")
    StudentCourse selectByCondition(StudentCourse studentCourse);

    List<StudentCourse> selectAll(StudentCourse studentCourse);

    @Select("select * from student_course where course_id = #{courseId}")
    List<StudentCourse> selectByCourseId(Integer id);

    @Select("select * from student_course where id = #{id}")
    StudentCourse findById(Integer id);

    @Select("select courses.teacher_id from courses where id = #{courseId}")
    int findTeacherId(StudentCourse studentCourse);

    @Update("update student_course set state = #{state} where course_id = #{courseId} and student_id = #{studentId}")
    void updateState(Score score);

    @Select("select * from student_course where course_name = #{courseName} " +
            "and course_no = #{courseNo} " +
            "and teacher_name = #{teacherName} " +
            "and semester = #{semester}")
    List<StudentCourse> selectByCondition2(Score score);


    @Select("select * from student_course where course_id = #{courseId} and student_id = #{studentId}")
    StudentCourse findByStudentAndCourseId(Score dbscore);

    @Update("update student_course set state = '未打分' where id = #{id}")
    void updateStateById(Integer id);
}
