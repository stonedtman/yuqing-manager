package com.stonedt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/userRecords")
public class userRecordsController {


    @RequestMapping("")
    public ModelAndView userRecords(ModelAndView modelAndView) {

        modelAndView.setViewName("userRecords");
        modelAndView.addObject("left", "home");
        return modelAndView;
    }
}
