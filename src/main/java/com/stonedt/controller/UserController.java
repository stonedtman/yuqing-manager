package com.stonedt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/user"})
public class UserController {
  @Autowired
  UserService userService;
  
  @RequestMapping({"/list"})
  public ModelAndView toUserList(ModelAndView mv) {
    mv.setViewName("userList");
    mv.addObject("left", "user");
    return mv;
  }


  
  @PostMapping({"/getUserList"})
  @ResponseBody
  public String getUserList(@RequestBody JSONObject dataObject) {
    Map<String, Object> map = new HashMap<>();

    Integer page = dataObject.getInteger("page");
    String keyword = dataObject.getString("keyword");
    String organization_id = dataObject.getString("organization_id");


    map.put("page", page);
    map.put("keyword", keyword);
    map.put("organization_id", organization_id);
    JSONObject response = userService.getUserList(map);
    return response.toJSONString();
  }
  
  @GetMapping({"/register"})
  public ModelAndView register(ModelAndView mv) {

    List data = userService.getOrganizationList();
    mv.addObject("data" , JSON.toJSONString(data));
    mv.setViewName("register");
    return mv;
  }

  @GetMapping({"/userList"})
  public ModelAndView userList(ModelAndView mv) {
    mv.setViewName("userList");
    return mv;
  }


  
  @PostMapping({"/addUserInfo"})
  @ResponseBody
  public String addUserInfo(@RequestBody String data) {
    JSONObject dataJson = JSON.parseObject(data);
    JSONObject response = this.userService.addUser((Map)dataJson);
    return response.toJSONString();
  }




  @RequestMapping(value = "/updateUser")
  public String getUser(String oldTelephone, Model model){
    /*String telephone = oldTelephone.getString("oldTelephone");*/
    Map data = userService.getUser(oldTelephone);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String nowDate = simpleDateFormat.format(new Date());
    data.put("nowDate" , nowDate);
    model.addAttribute("data",data);
    return "updateUser";
  }

  @RequestMapping(value = "/updateUserByTelephone")
  @ResponseBody
  public JSONObject updateUserByTelephone(@RequestBody JSONObject userData){

    String message = userService.updateUserByTelephone(userData);
    JSONObject object = new JSONObject();
    object.put("message",message);

    return object;
  }


  @RequestMapping(value = "/updateUserState")
  @ResponseBody
  public JSONObject updateUserState(@RequestBody Map object){


    JSONObject jo = userService.updateUserState(object);
    return jo;
  }


  @RequestMapping(value = "updatePassword")
  @ResponseBody
  public JSONObject updatePassword(@RequestBody Map data){

    JSONObject jo = userService.updatePassword(data);

    return jo;
  }


/*  @RequestMapping(value = "/transferProject")
  @ResponseBody
  public JSONObject transferProject(@RequestBody Map data){

    JSONObject jo = userService.transferProject(data);

    return null;
  }*/

  @RequestMapping("/getCompanyList")
  @ResponseBody
  public String getCompanyList(){

    JSONObject jo = new JSONObject();
    JSONObject mes = userService.getCompanyList(jo);

    return mes.toJSONString();
  }


}
