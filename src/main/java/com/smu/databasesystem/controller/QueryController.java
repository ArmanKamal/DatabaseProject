package com.smu.databasesystem.controller;

import com.smu.databasesystem.model.Departments;
import com.smu.databasesystem.model.FacultyMembers;
import com.smu.databasesystem.model.FacultyRank;
import com.smu.databasesystem.model.Programs;
import com.smu.databasesystem.model.query.DepartmentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class QueryController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

    @GetMapping("/departments")
    public ModelAndView showDepartmentPage(@ModelAttribute("Departments") Departments departments) {
        ModelAndView mav = new ModelAndView("department");
        mav.addObject("departments", getDepartments());
        return mav;
    }

    private List<Departments> getDepartments() {
        String sql = "SELECT * FROM Departments";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Departments.builder()
                .departmentCode(resultSet.getString("department_code"))
                .departmentName(resultSet.getString("department_name"))
                .build());
    }

    @PostMapping("/displayDepartmentDetails")
    public String departmentDetail(@RequestParam String departmentCode, RedirectAttributes redirectAttributes, Model model) {
        try {
            model.addAttribute("programDetails", getProgramsForDepartment(departmentCode));
            model.addAttribute("faculties", getFacultyForDepartment(departmentCode));
            model.addAttribute("departments", getDepartments());
            model.addAttribute("departmentMessage", departmentCode);

        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);

        }
        return "department";
    }

    public List<Programs> getProgramsForDepartment(String departmentCode) {
        String sql = "SELECT program_id, program_name FROM Programs WHERE department_code = ?";

        return jdbcTemplate.query(sql, new Object[]{departmentCode}, (resultSet, rowNum) -> Programs.builder()
                .programId((long) resultSet.getInt("program_id"))
                .programName(resultSet.getString("program_name"))
                .build());
    }

    public List<DepartmentDetails> getFacultyForDepartment(String departmentCode) {
        String sql = "SELECT fm.faculty_id, fm.faculty_name, fm.email_address, fm.faculty_rank, " +
                "d.department_name AS department, p.program_name AS program_in_charge " +
                "FROM FacultyMembers fm " +
                "JOIN Departments d ON fm.department_code = d.department_code " +
                "LEFT JOIN Programs p ON fm.faculty_id = p.head_of_program_id AND fm.department_code = p.department_code " +
                "WHERE fm.department_code = ?";

        return jdbcTemplate.query(sql, new Object[]{departmentCode}, (resultSet, rowNum) -> DepartmentDetails.builder()
                .facultyId(String.valueOf(resultSet.getInt("faculty_id")))
                .facultyName(resultSet.getString("faculty_name"))
                .facultyEmail(resultSet.getString("email_address"))
                .facultyRank(FacultyRank.getByLabel(resultSet.getString("faculty_rank")))
                .programInCharge(resultSet.getString("program_in_charge"))
                .build());
    }
}
