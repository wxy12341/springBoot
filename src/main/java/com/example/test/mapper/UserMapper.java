package com.example.test.mapper;

import com.example.test.bean.UserBean;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserBean getInfo(String name,String password);
//    UserBean loadUserByUsername(@Param("username")String username);//查询用户名
}