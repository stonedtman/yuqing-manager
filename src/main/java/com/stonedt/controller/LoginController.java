package com.stonedt.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.stonedt.service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class LoginController {
  @Autowired
  UserService userService;

  @Autowired
  private DefaultKaptcha defaultKaptcha;

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
  public String verifyAcount(@RequestParam("telephone") String telephone,
                             @RequestParam("password") String password,
                             @RequestParam("code") String code,
                             HttpSession session) {
    String checkCode = null;//图形验证码
    if(null!=session.getAttribute(Constants.KAPTCHA_SESSION_KEY)) {
      checkCode = session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
    }
    if (checkCode == null || !checkCode.toString().equals(code)) {
      JSONObject response = new JSONObject();
      response.put("code", "500");
      response.put("msg", "验证码错误");
      return response.toJSONString();
    }

    JSONObject response = this.userService.verifyAcount(telephone, password, session);
    return response.toJSONString();
  }


  @GetMapping(value = "/code")
  public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
    byte[] captchaChallengeAsJpeg;
    ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
    /**
     * 生成验证码字符串并保存到session中
     */
    String createText = defaultKaptcha.createText();
    HttpSession session = request.getSession();
    session.setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
//        session.setMaxInactiveInterval(60);
    System.out.println(createText);
//        getyzm(createText);
//        存储数据库
    //Yzm yzm = new Yzm(null, createText);
    //yzmMapper.addYzm(yzm);

    /**
     * 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
     */
    BufferedImage challenge = defaultKaptcha.createImage(createText);
    try {
      ImageIO.write(challenge,"jpg",jpegOutputStream);
    } catch (IOException e) {
      // log.error("生成图形验证码失败",e);
      throw new Exception("error");    // 抛出异常，可以不用关心
    }
    /**
     * 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
     */
    captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires",0);
    response.setContentType("image/jpeg");
    try {
      ServletOutputStream servletOutputStream = response.getOutputStream();
      servletOutputStream.write(captchaChallengeAsJpeg);
      servletOutputStream.flush();
      servletOutputStream.close();
    } catch (IOException e) {
      //log.error("输出验证码失败",e);
//            throw new BusinessException(ErrorEnum.CRATE_IMAGE_ERROR);	// 抛出异常，可以不用关心
    }
  }
}
