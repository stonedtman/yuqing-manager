package com.stonedt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/statistics/detail")
public class statisticsDetailController {


    @RequestMapping("")
    public ModelAndView statisticsDetailController(ModelAndView modelAndView) {

        modelAndView.setViewName("statisticsDetail");
        modelAndView.addObject("left", "statistics");
        return modelAndView;
    }
}
