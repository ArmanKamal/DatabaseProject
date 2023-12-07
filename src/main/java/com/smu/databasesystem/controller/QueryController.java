package com.smu.databasesystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QueryController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/departments")
    public ModelAndView showDepartmentPage() {
        ModelAndView mav = new ModelAndView("department");

        return mav;
    }
}
