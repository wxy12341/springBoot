package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.test.bean.UserBean;
import com.example.test.service.UserService;
import com.example.test.serviceImpl.UserServiceImpl;
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
    @ResponseBody
    @RequestMapping(value = "/selectUserList",method = RequestMethod.POST)
    public String selectUserList(@RequestBody String param){
        JSONObject jsonObj = JSONObject.parseObject(param);
        JSONObject jsonObjParam = JSONObject.parseObject(jsonObj.get("param").toString());



        // return "success";
        JSONObject jsonObjResponse = new JSONObject();
        List<UserBean> list =userService.selectUserList();
        if(list!=null &&list.size()>0){
            jsonObjResponse.put("res","success");
            jsonObjResponse.put("userBean",list);

        }else {
            jsonObjResponse.put("res","error");
            jsonObjResponse.put("message","查询错误!");
        }
        return jsonObjResponse.toString();
    }
}
