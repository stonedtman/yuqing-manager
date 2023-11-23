package com.stonedt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
public class HomeController {


    @RequestMapping("")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.setViewName("home");
        modelAndView.addObject("left", "home");
        return modelAndView;
    }
}
