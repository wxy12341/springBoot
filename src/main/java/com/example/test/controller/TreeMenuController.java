package com.example.test.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.test.bean.TreeMenu;
import com.example.test.service.TreeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TreeMenuController {

    @Autowired
    TreeMenuService treeMenuService;

    @ResponseBody
    @RequestMapping(value = "/getTreeMenu",method = RequestMethod.POST)
    public String login(@RequestBody String param){
        JSONObject jsonObj = JSONObject.parseObject(param);
        JSONObject jsonObjParam = JSONObject.parseObject(jsonObj.get("param").toString());
        JSONObject jsonResponse =  new JSONObject();
        List<TreeMenu> treeMenu = treeMenuService.getTreeMenu();
        if(treeMenu!=null){
            jsonResponse.put("res","success");
            jsonResponse.put("treeMenu",treeMenu);
        }else{
            jsonResponse.put("res","error");
            jsonResponse.put("message","暂无数据!");
        }
        return jsonResponse.toString();
    }
}
