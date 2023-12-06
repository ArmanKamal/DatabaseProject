package com.smu.databasesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    @GetMapping("/homePage")
    public ModelAndView showView(){
        ModelAndView mav = new ModelAndView("index.html");
        return mav;
    }
}


