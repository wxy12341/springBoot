package com.example.test.mapper;

import com.example.test.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE name = #{name} AND password = #{password}")
    UserBean getInfo(String name,String password);

    @Select("SELECT * FROM user WHERE id = #{id} ")
    UserBean findUserById(String id);
//    UserBean loadUserByUsername(@Param("username")String username);//查询用户名

    @Select("SELECT * FROM user")
    List<UserBean> selectUserList();
}