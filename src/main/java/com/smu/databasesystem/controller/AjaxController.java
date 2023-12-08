package com.smu.databasesystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AjaxController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/fetchCourses")
    public List<Map<String, Object>> fetchCourses(@RequestParam("programId") String programId) {

        System.out.println("Program id" + programId);
        String sql = "SELECT pc.course_id, c.course_title, l.objective_code, s.sub_objective_code " +
                "FROM ProgramCourses pc " +
                "LEFT JOIN Courses c ON pc.course_id = c.course_id " +
                "LEFT JOIN LearningObjectives l ON pc.program_id = l.program_id " +
                "LEFT JOIN SubObjectives s ON l.objective_code = s.objective_code " +
                "WHERE pc.program_id = ? " +
                "GROUP BY pc.course_id, c.course_title, l.objective_code, s.sub_objective_code";
        List<Map<String, Object>> courses = jdbcTemplate.queryForList(sql, programId);

        return courses;
    }
}
