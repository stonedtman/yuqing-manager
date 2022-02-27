package com.stonedt.controller;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.service.LogService;
import com.stonedt.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/userlog"})
public class UserLogController {
  @Autowired
  LogService logService;
  
  @RequestMapping({"/loginlist"})
  public ModelAndView getLogLoginList(ModelAndView mv) {
    mv.setViewName("loginlog");
    mv.addObject("left", "userlogin");
    mv.addObject("nav", "log");
    return mv;
  }
  
  @PostMapping({"getLogList"})
  @ResponseBody
  public String getLogList(@RequestBody JSONObject dataObject) {
    JSONObject paramJson = new JSONObject();

    Integer time = dataObject.getInteger("time");
    Integer page = dataObject.getInteger("page");
    String keyword = dataObject.getString("keyword");


    paramJson.put("page", page);
    String times = "";
    String timee = "";
    JSONObject timeJson = new JSONObject();
    if (time.intValue() == 0) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(0));
    } else if (time.intValue() == 1) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-2));
    } else if (time.intValue() == 2) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-6));
    } else if (time.intValue() == 3) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-14));
    } else if (time.intValue() == 4) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-29));
    } 
    times = timeJson.getString("times") + " 00:00:00";
    timee = timeJson.getString("timee") + " 23:59:59";
    paramJson.put("times", times);
    paramJson.put("timee", timee);
    paramJson.put("keyword", keyword);
    JSONObject response = logService.getLoginList(paramJson);
    return response.toJSONString();
  }
  
  @RequestMapping({"/uselist"})
  public ModelAndView getLogUseList(ModelAndView mv) {
    mv.addObject("left", "useruse");
    mv.addObject("nav", "log");
    mv.setViewName("uselog");
    return mv;
  }
  
  @PostMapping({"getLogUseList"})
  @ResponseBody
  public JSONObject getLogUseList(@RequestBody JSONObject dataObject) {
    JSONObject paramJson = new JSONObject();

    Integer page = dataObject.getInteger("page");
    Integer time = dataObject.getInteger("time");
    String keyword = dataObject.getString("keyword");

    paramJson.put("page", page);
    String times = "";
    String timee = "";
    JSONObject timeJson = new JSONObject();
    if (time.intValue() == 0) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(0));
    } else if (time.intValue() == 1) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-2));
    } else if (time.intValue() == 2) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-6));
    } else if (time.intValue() == 3) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-14));
    } else if (time.intValue() == 4) {
      timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(-29));
    } 
    times = timeJson.getString("times") + " 00:00:00";
    timee = timeJson.getString("timee") + " 23:59:59";
    paramJson.put("times", times);
    paramJson.put("timee", timee);
    paramJson.put("keyword", keyword);
    JSONObject response = this.logService.getUseList(paramJson);
    return response;
  }
}
