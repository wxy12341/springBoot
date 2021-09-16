package com.example.test.service;

import com.example.test.bean.TreeMenu;
import com.example.test.mapper.MenuDao;
import com.example.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreeMenuService {

    //将DAO注入Service层
    @Autowired
    private MenuDao menuDao;

    public List<TreeMenu> getTreeMenu() {
        return menuDao.getTreeMenu();
    }
}