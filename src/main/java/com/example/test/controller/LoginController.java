package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.test.bean.UserBean;
import com.example.test.service.UserService;
import com.example.test.utils.RedisUtils;
import com.example.test.utils.UserLoginToken;
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
        //获取redis里面缓存的验证码
        String yzmText = redisUtils.get("yzm").toString();
        //通过用户名密码获取用户信息
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
            //登录成功后生成token信息
            String token= JWT.create().withAudience(String.valueOf(userBean.getId()))
                    .sign(Algorithm.HMAC256(userBean.getPassword()));
            //将生成的token缓存到redis里面
            redisUtils.set("token",token);
            jsonObjResponse.put("res","success");
            jsonObjResponse.put("userBean",JSONObject.toJSONString(userBean));
            jsonObjResponse.put("token",token);
        }else {
            jsonObjResponse.put("res","error");
            jsonObjResponse.put("message","登录失败,请检查姓名和密码是否正确!");
        }
        return jsonObjResponse.toString();
    }

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public String getUserInfo(@RequestBody String param){
        JSONObject jsonObj = JSONObject.parseObject(param);
        JSONObject jsonObjParam = JSONObject.parseObject(jsonObj.get("param").toString());
        JSONObject jsonResponse = new  JSONObject();
        String id = jsonObjParam.get("id").toString();
        UserBean userBean = userService.findUserById(id);
        if(userBean!=null){
            jsonResponse.put("res","success");
            jsonResponse.put("userBean",JSONObject.toJSONString(userBean));
        }else{
            jsonResponse.put("res","error");
            jsonResponse.put("message","用户不存在!");
        }
        return jsonResponse.toString();
    }

}