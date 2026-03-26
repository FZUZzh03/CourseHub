package com.zzh.coursehub.mapper;

import com.zzh.coursehub.entity.dto.PageDTO;
import com.zzh.coursehub.entity.pojo.User;
import com.zzh.coursehub.entity.vo.PageResult;
import com.zzh.coursehub.entity.vo.UserInfoVO;
import com.zzh.coursehub.entity.vo.UserLoginVO;
import com.zzh.coursehub.entity.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select id,username,password,role from user where username = #{username} and is_deleted = 0")
    UserLoginVO login(String username);

    @Select("select id,username,name,role,age from user where id = #{id} and is_deleted = 0")
    UserVO getById(Long id);

    @Select("select * from user where id = #{id} and is_deleted = 0")
    User getUserById(Long id);

    List<UserInfoVO> getAll(@Param("pageDTO")PageDTO pageDTO);

    Long countAll();

    @Select("select * from user where username = #{username} and is_deleted = 0")
    User getByUsername(String username);

    @Select("select * from user where name = #{name} and is_deleted = 0")
    User getByname(String name);


    @Insert("insert into user(username,password,name,role,age) values (#{username},#{password},#{name},#{role},#{age})")
    int add(User user);

    @Select("select password from user where id = #{id}")
    String getPwd(Long id);
    @Update("update user set password = #{password},update_time = NOW() where id = #{id}")
    int updatePwd(String password,Long id);
}
