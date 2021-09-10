package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.test.bean.UserBean;
import com.example.test.service.UserService;
import com.example.test.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    //将Service注入Web层
    @Autowired
    UserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/login")
    public String show(){
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/loginIn",method = RequestMethod.POST)
    public String login(@RequestBody String param){

        JSONObject jsonObj = JSONObject.parseObject(param);
        JSONObject jsonObjParam = JSONObject.parseObject(jsonObj.get("param").toString());
        String name = jsonObjParam.get("name").toString();
        String password = jsonObjParam.get("password").toString();
        String yzmText = redisUtils.get("yzm").toString();

        UserBean userBean = userService.loginIn(name,password);
       // return "success";
        JSONObject jsonObjResponse = new JSONObject();

        if(!StringUtils.isEmpty(yzmText)){
            if(!yzmText.equals(jsonObjParam.get("code").toString())) {
                jsonObjResponse.put("res", "error");
                jsonObjResponse.put("message", "验证码错误!");
                return jsonObjResponse.toString();
            }
        }else{
            jsonObjResponse.put("res","error");
            jsonObjResponse.put("message","验证码不能为空!");
            return jsonObjResponse.toString();
        }
        if(userBean!=null){
            jsonObjResponse.put("res","success");
            jsonObjResponse.put("userBean",JSONObject.toJSONString(userBean));

        }else {
            jsonObjResponse.put("res","error");
            jsonObjResponse.put("message","登录失败,请检查姓名和密码是否正确!");
        }
        return jsonObjResponse.toString();
    }



}