package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.test.bean.UserBean;
import com.example.test.service.UserService;
import com.example.test.serviceImpl.UserServiceImpl;
import com.example.test.utils.UserLoginToken;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    //将Service注入Web层
    @Autowired
    UserServiceImpl userService;

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/selectUserList",method = RequestMethod.POST)
    public String selectUserList(@RequestBody String param){
        JSONObject jsonObj = JSONObject.parseObject(param);
        JSONObject jsonObjParam = JSONObject.parseObject(jsonObj.get("param").toString());
        String pageNum = jsonObjParam.get("pageNum").toString();
        String pageSize = jsonObjParam.get("pageSize").toString();
        // return "success";
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        JSONObject jsonObjResponse = new JSONObject();
        Page<UserBean> page = ( Page<UserBean>) userService.selectAll();
//        List<UserBean> list = userService.selectAll();
        int total = userService.selectCount();
        if(page!=null &&page.size()>0){
            jsonObjResponse.put("res","success");
//            jsonObjResponse.put("userBean",list);
            jsonObjResponse.put("total",total);
            jsonObjResponse.put("page",page);
        }else {
            jsonObjResponse.put("res","error");
            jsonObjResponse.put("message","查询错误!");
        }
        return jsonObjResponse.toString();
    }
}
