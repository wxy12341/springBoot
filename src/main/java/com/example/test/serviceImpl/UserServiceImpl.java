package com.example.test.serviceImpl;

import com.example.test.bean.UserBean;
import com.example.test.mapper.UserMapper;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    //将DAO注入Service层
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean loginIn(String name, String password) {
        return userMapper.getInfo(name,password);
    }

    @Override
    public UserBean findUserById(String id) {
        return userMapper.findUserById(id);
    }

    @Override
    public List<UserBean> selectUserList() {
        return userMapper.selectUserList();
    }

    @Override
    public List<UserBean> selectAll() {
        return userMapper.selectAll();
    }

    public int selectCount(){
        UserBean userbean = new UserBean();
        return userMapper.selectCount(userbean);
    }
}