package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface StudentMapper {

    @Select("select * from student where name like concat('%',#{name}, '%') and student_number like concat('%',#{studentNumber}, '%')")
    List<Student> selectAll(Student student);

    @Select("select * from student where student_number = #{studentNumber}")
    Student findByStudentNumber(String studentNumber);

    @Insert("insert into student (student_number, password, name, gender, age, grade, department, major, email, phone_number) " +
            "values (#{studentNumber}, #{password}, #{name}, #{gender}, #{age}, #{grade}, #{department}, #{major}, #{email}, #{phoneNumber})")
    void insert(Student dbstudent);

    @Delete("delete from student where id = #{id}")
    void deleteById(Integer id);

    @Update("update student set student_number = #{studentNumber}, name = #{name}, gender = #{gender}, age = #{age},password = #{password}," +
            "grade =  #{grade},  department = #{department}, major = #{major}, email = #{email}, phone_number = #{phoneNumber} where id = #{id}")
    void update(Student student);

    @Select("select * from student where id = #{id}")
    Student findById(Integer id);

    @Select("select * from student where name = #{studentName}")
    Student findByStudentName(String studentName);

    @Select("select * from student where grade = #{grade}")
    List<Student> findByGrade(String grade);
    @Select("select * from student where department = #{department}")
    List<Student> findByDept(String department);
}
