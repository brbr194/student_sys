package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper

public interface StudentMapper {

    @Select("select * from student where student_name like concat('%',#{studentName}, '%') and student_number like concat('%',#{studentNumber}, '%')")
    List<Student> selectAll(Student student);

    @Select("select * from student where student_number = #{studentNumber}")
    Student findByStudentNumber(String studentNumber);

    @Insert("insert into student (student_number, password, student_name, gender, birth_date, grade, department, major, email, phone_number) " +
            "values (#{studentNumber}, #{password}, #{studentName}, #{gender}, #{birthDate}, #{grade}, #{department}, #{major}, #{email}, #{phoneNumber})")
    void insert(Student dbstudent);
}
