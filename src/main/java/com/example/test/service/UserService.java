package com.example.test.service;

import com.example.test.bean.UserBean;

import java.util.List;

public interface UserService {

    UserBean loginIn(String name,String password);
    UserBean findUserById(String id);
    List<UserBean> selectUserList();
}