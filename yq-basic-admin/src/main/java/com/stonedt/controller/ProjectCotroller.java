package com.stonedt.controller;


import com.alibaba.fastjson.JSONObject;
import com.stonedt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/project")
@Controller
public class ProjectCotroller {
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/list")
    public ModelAndView projectList(ModelAndView mv){

        mv.setViewName("projectList");
        mv.addObject("left", "project");
        return mv;
    }

    @RequestMapping(value = "getUsers")
    @ResponseBody
    public JSONObject getUsers(){

        JSONObject users = projectService.getUsers();
        return users;
    }

    @RequestMapping(value = "getGroupListByuserId")
    @ResponseBody
    public JSONObject getGroupListByuserId(String userId){

        JSONObject data = projectService.getGroupListByuserId(userId);

        return data;
    }

    @RequestMapping(value = "getProjectListByGroupId")
    @ResponseBody
    public JSONObject getProjectListByGroupId(@RequestBody Map map){

        JSONObject jo = projectService.getProjectListByGroupId(map);

        return jo;
    }

    @RequestMapping("/getProjectListByUserId")
    @ResponseBody
    public JSONObject getProjectListByUserId(@RequestBody Map map){
        JSONObject projectListByuserId = projectService.getProjectListByuserId(map);

        return projectListByuserId;
    }

    @RequestMapping("/getProjectList")
    @ResponseBody
    public JSONObject getProjectList(@RequestBody Map map){
        JSONObject projectList = projectService.getProjectList(map);
        return projectList;
    }


    @RequestMapping("copy")
    @ResponseBody
    public JSONObject copy(@RequestBody Map data){

        JSONObject jo = projectService.copy(data);
        return jo;
    }
}
