package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Select("select * from admin where username = #{username}")
    Admin findByUserName(String username);

    /**
     * 条件查询
     */
    @Select("select * from admin where name like concat('%',#{name}, '%') and username like concat('%',#{username}, '%')")
    List<Admin> selectAll(Admin admin);

    /**
     * 新增
     * @param admin
     */
    @Insert("insert into admin (username, password, name) values (#{username}, #{password}, #{name});")
    void insert(Admin admin);

    /**
     * 更新
     * @param admin
     */
    @Update("update admin set name = #{name}, username = #{username}, password = #{password} where id = #{id}")
    void updateById(Admin admin);

    /**
     * 删除
     * @param id
     */
    @Delete("delete from admin where id = #{id}")
    void deleteById(Integer id);
}
