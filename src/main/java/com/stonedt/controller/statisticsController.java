package com.stonedt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/statistics")
public class statisticsController {


    @RequestMapping("")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.setViewName("statistics");
        modelAndView.addObject("left", "statistics");
        return modelAndView;
    }
}
