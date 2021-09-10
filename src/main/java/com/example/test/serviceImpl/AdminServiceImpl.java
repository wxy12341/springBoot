//package com.example.test.serviceImpl;
//
//import java.util.Date;
//
//import com.example.test.bean.UserBean;
//import com.example.test.mapper.UserMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import cn.hutool.core.util.StrUtil;
//
//@Service
//public class AdminServiceImpl implements UserDetailsService{
//    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
//
//    @Autowired
//    private UserMapper adminMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserBean admin = adminMapper.loadUserByUsername(username);
//        if(admin == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//        logger.info("{}登录了",username);
//        return admin;
//    }
//}
//
