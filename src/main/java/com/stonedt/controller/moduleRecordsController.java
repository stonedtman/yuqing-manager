package com.stonedt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/moduleRecords")
public class moduleRecordsController {


    @RequestMapping("")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.setViewName("moduleRecords");
        modelAndView.addObject("left", "home");
        return modelAndView;
    }
}
