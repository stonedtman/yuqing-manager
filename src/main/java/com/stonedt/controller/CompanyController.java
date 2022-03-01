package com.stonedt.controller;


import com.alibaba.fastjson.JSONObject;
import com.stonedt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @RequestMapping({"/list"})
    public ModelAndView toCompanyList(ModelAndView mv) {
        mv.setViewName("companyList");
        mv.addObject("left", "company");
        return mv;
    }

    @RequestMapping({"/toUserList"})
    public ModelAndView toUserList(String organization_id , ModelAndView mv) {
        mv.setViewName("userList");
        mv.addObject("left", "user");
        mv.addObject("organization_id" , organization_id);
        return mv;
    }

    @GetMapping("/updateCompany")
    public ModelAndView updateCompany(String id , ModelAndView mv){

        JSONObject data = companyService.getCompanyById(id);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        data.put("nowDate" , date);
        mv.addObject("data" , data);

        mv.setViewName("updateCompany");
        return mv;
    }


    @GetMapping("/registerCompany")
    public ModelAndView registerCompany(ModelAndView mv){
        mv.setViewName("registerCompany");
        return mv;
    }


    @RequestMapping(value = "/getCompanyList")
    @ResponseBody
    public String getCompanyList(@RequestParam(value = "page", defaultValue = "1") Integer page){


        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        JSONObject response = this.companyService.getCompanyList(map);
        return response.toJSONString();
    }

    @PostMapping("/addCompany")
    @ResponseBody
    public JSONObject addCompany(@RequestBody Map data){

        JSONObject response = companyService.addCompany(data);
        return response;
    }


    @RequestMapping(value = "updateCompanyByOrganizationId")
    @ResponseBody
    public JSONObject updateCompanyByOrganizationId(@RequestBody Map data){

        JSONObject response = companyService.updateCompany(data);
        return response;
    }




}
