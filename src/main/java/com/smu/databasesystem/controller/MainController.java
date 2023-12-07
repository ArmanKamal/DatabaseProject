package com.smu.databasesystem.controller;

import com.smu.databasesystem.model.*;
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
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();


    @GetMapping("/")
    public ModelAndView showView(@ModelAttribute("Departments") Departments departments,
                                 @ModelAttribute("Persons") Persons persons,
                                 @ModelAttribute("Programs") Programs programs,
                                 @ModelAttribute("FacultyMembers") FacultyMembers facultyMembers,
                                 @ModelAttribute("Courses") Courses courses,
                                 @ModelAttribute("Sections") Sections sections) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("departments", getDepartments());
        mav.addObject("persons", getPersons());
        mav.addObject("courses", getCourses());
        mav.addObject("faculties", getFacultyMembers());
        return mav;
    }

    /* Create Department */
    @PostMapping("/processDepartmentForm")
    public String processDepartmentForm(@ModelAttribute("Departments") Departments departments, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO Departments (department_code, department_name) VALUES (?, ?)",
                    departments.getDepartmentCode(), departments.getDepartmentName());

            String successMessage = "Department '" + departments.getDepartmentName() + "' created successfully!";
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
                    persons.getUniversityId(), persons.getPersonName(), persons.getEmail());

            String successMessage = "Person Information for " + persons.getPersonName() + "' created successfully!";
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

    @PostMapping("/processFacultyForm")
    public String processFacultyForm(@ModelAttribute("facultyMember") FacultyMembers facultyMember, RedirectAttributes redirectAttributes) {
        try {
            String successMessage = "Faculty member '" + facultyMember.getFacultyName() + "' created successfully!";
            jdbcTemplate.update("INSERT INTO FacultyMembers (faculty_name, email_address, faculty_rank, department_code) VALUES (?, ?, ?, ?)",
                    facultyMember.getFacultyName(), facultyMember.getEmailAddress(), facultyMember.getFacultyRank().name(), facultyMember.getDepartmentCode());

            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    @PostMapping("/processCourseForm")
    public String processCourseForm(@ModelAttribute("Courses") Courses course, RedirectAttributes redirectAttributes) {
        try {
            String courseId = course.getDepartmentCode()+course.getCourseId();
            course.setCourseId(courseId);

            jdbcTemplate.update("INSERT INTO Courses (course_id, department_code, course_title,course_description) VALUES (?, ?, ?,?)",
                    courseId, course.getDepartmentCode(), course.getCourseTitle(), course.getCourseDescription());

            String successMessage = "Course '" + course.getCourseTitle() + "' created successfully with ID: " + courseId;
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }
    @PostMapping("/processSectionForm")
    public String processSectionForm(@ModelAttribute("Sections") Sections section, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO Sections (course_id, semester, faculty_id, enrolled_students) VALUES (?, ?, ?, ?)",
                    section.getCourseId(), section.getSemester().name(), section.getFacultyId(), section.getEnrolledStudents());

            String successMessage = "Section created successfully for course ID: " + section.getCourseId() +
                    ", Semester: " + section.getSemester() + ", Section Number: " + section.getSectionNumber();
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
                .departmentCode(resultSet.getString("department_code"))
                .departmentName(resultSet.getString("department_name"))
                .build());
    }

    private List<Persons> getPersons() {
        String sql = "SELECT * FROM Persons";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Persons.builder()
                .universityId(resultSet.getString("university_id"))
                .personName(resultSet.getString("person_name"))
                .email(resultSet.getString("email"))
                .build());
    }

    private List<Courses> getCourses() {
        String sql = "SELECT * FROM Courses";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Courses.builder()
                .courseId(resultSet.getString("course_id"))
                .courseDescription(resultSet.getString("course_description"))
                .courseTitle(resultSet.getString("course_title"))
                .departmentCode(resultSet.getString("department_code"))
                .build());
    }
    private List<FacultyMembers> getFacultyMembers() {
        String sql = "SELECT * FROM FacultyMembers";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> FacultyMembers.builder()
                .facultyId(resultSet.getString("faculty_id"))
                .facultyName(resultSet.getString("faculty_name"))
                .facultyRank(FacultyRank.getByLabel(resultSet.getString("faculty_rank")))
                .emailAddress(resultSet.getString("email_address"))
                .departmentCode(resultSet.getString("department_code"))
                .build());
    }
}



