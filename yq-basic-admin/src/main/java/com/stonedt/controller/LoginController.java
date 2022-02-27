package com.stonedt.controller;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
  @Autowired
  UserService userService;
  
  @GetMapping({"/login"})
  public ModelAndView toLogin(ModelAndView mv) {
    mv.setViewName("login");
    return mv;
  }
  
  @GetMapping({"/logout"})
  public void logout(HttpServletResponse response, HttpServletRequest request) {
    try {
      request.getSession().removeAttribute("User");
      response.sendRedirect(request.getContextPath() + "/login");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @PostMapping({"/verifyacount"})
  @ResponseBody
  public String verifyAcount(@RequestParam("telephone") String telephone, @RequestParam("password") String password, HttpSession session) {
    JSONObject response = this.userService.verifyAcount(telephone, password, session);
    return response.toJSONString();
  }
}
