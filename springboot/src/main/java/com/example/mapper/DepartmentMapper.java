package com.example.mapper;

import com.example.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from departments")
    List<Department> findAll();

    @Delete("delete from departments where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from departments where id = #{id}")
    Department findById(Integer id);

    @Select("select * from departments where department = #{name}")
    Department findByDepartmentName(String name);

    @Insert("insert into departments (department) values (#{department})")
    void insert(Department department);
    @Update("update departments set department = #{department}, updated_time = #{updatedTime} where id = #{id}")
    void update(Department department);

    @Select("select * from departments where department like concat('%',#{department}, '%')")
    List<Department> selectAll(Department department);

    @Select("select * from departments where department = #{departmentName}")
    Department findByDeptName(String departmentName);
}
