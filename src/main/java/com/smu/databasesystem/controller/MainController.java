package com.smu.databasesystem.controller;

import com.smu.databasesystem.model.Departments;
import com.smu.databasesystem.model.Persons;
import com.smu.databasesystem.model.Programs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();


    @GetMapping("/")
    public ModelAndView showView(@ModelAttribute("Departments") Departments departments,
                                 @ModelAttribute("Persons") Persons persons,
                                 @ModelAttribute("Programs") Programs programs) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("departments", getDepartments());
        mav.addObject("persons", getPersons());
        return mav;
    }

    /* Create Department */
    @PostMapping("/processDepartmentForm")
    public String processDepartmentForm(@ModelAttribute("Departments") Departments departments, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO Departments (department_code, department_name) VALUES (?, ?)",
                    departments.getDepartment_code(), departments.getDepartment_name());

            String successMessage = "Department '" + departments.getDepartment_name() + "' created successfully!";
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);

        }
        return "redirect:/";
    }

    /* Create Person */
    @PostMapping("/processPersonForm")
    public String processPersonForm(@ModelAttribute("Persons") Persons persons, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO Persons (university_id, person_name,email) VALUES (?, ?,?)",
                    persons.getUniversity_id(), persons.getPerson_name(), persons.getEmail());

            String successMessage = "Person Information for " + persons.getPerson_name() + "' created successfully!";
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    /* Create Program/Degrees */
    @PostMapping("/processProgramForm")
    public String processProgramForm(@ModelAttribute("Programs") Programs program, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Came Here");
            String successMessage = "Program '" + program.getProgramName() + "' created successfully!";
            jdbcTemplate.update("INSERT INTO Programs (program_name, department_code, head_of_program_id) VALUES (?, ?, ?)",
                    program.getProgramName(), program.getDepartmentCode(), program.getHeadOfProgramId());

            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    private List<Departments> getDepartments() {
        String sql = "SELECT * FROM Departments";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Departments.builder()
                .department_code(resultSet.getString("department_code"))
                .department_name(resultSet.getString("department_name"))
                .build());
    }

    private List<Persons> getPersons() {
        String sql = "SELECT * FROM Persons";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Persons.builder()
                .university_id(resultSet.getString("university_id"))
                .person_name(resultSet.getString("person_name"))
                .email(resultSet.getString("email"))
                .build());
    }
}



