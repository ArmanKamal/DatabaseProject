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
                                 @ModelAttribute("Sections") Sections sections,
                                 @ModelAttribute("LearningObjectives") LearningObjectives learningObjectives,
                                 @ModelAttribute("SubObjectives") SubObjectives subObjectives,
                                 @ModelAttribute("ProgramCourses") ProgramCourses programCourses,
                                 @ModelAttribute("ProgramObjectives") ProgramObjectives programObjectives,
                                 @ModelAttribute("EvaluationResults") EvaluationResults evaluationResults) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("departments", getDepartments());
        mav.addObject("courses", getCourses());
        mav.addObject("faculties", getFacultyMembers());
        mav.addObject("programs", getPrograms());
        mav.addObject("sections", getSections());
        mav.addObject("learningObjectives", getObjectives());
        mav.addObject("subObjectives", getSubObjectives());
        mav.addObject("programCourses", getProgramCourses());

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
            String courseId = course.getDepartmentCode() + course.getCourseId();
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
            jdbcTemplate.update("INSERT INTO Sections (course_id, semester, faculty_id, enrolled_students, year) VALUES (?, ?, ?, ?, ?)",
                    section.getCourseId(), section.getSemester().name(), section.getFacultyId(), section.getEnrolledStudents(), section.getYear());

            String successMessage = "Section created successfully for course ID: " + section.getCourseId() +
                    ", Semester: " + section.getSemester();
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    @PostMapping("/processLearningObjective")
    public String processSectionForm(@ModelAttribute("LearningObjectives") LearningObjectives learningObjectives, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO LearningObjectives (objective_code, program_id) VALUES (?,?)",
                    learningObjectives.getObjectiveCode(), learningObjectives.getProgramId());

            String successMessage = "Learning Objectives created successfully for program ID: " + learningObjectives.getProgramId();
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    @PostMapping("/processLearningSubObjective")
    public String processSectionForm(@ModelAttribute("SubObjectives") SubObjectives subObjectives, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO SubObjectives (objective_code, description) VALUES (?, ?)",
                    subObjectives.getObjectiveCode(), subObjectives.getDescription());

            String successMessage = "Sub Objectives created successfully for objective ID: " + subObjectives.getObjectiveCode();
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    @PostMapping("/processProgramCoursesForm")
    public String processSectionForm(@ModelAttribute("ProgramCourses") ProgramCourses programCourses, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO ProgramCourses (program_id, course_id) VALUES (?, ?)",
                    programCourses.getProgramId(), programCourses.getCourseId());

            String successMessage = "Program-Course pair successfully made for program ID " + programCourses.getProgramId() + " and course ID " + programCourses.getCourseId();
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    @PostMapping("/processProgramObjectivesForm")
    public String processSectionForm(@ModelAttribute("ProgramObjectives") ProgramObjectives programObjectives, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO ProgramObjectives (program_id, course_id, objective_code, sub_objective_code) VALUES (?, ?, ?, ?)",
                    programObjectives.getProgramId(), programObjectives.getCourseId(), programObjectives.getObjectiveCode(), programObjectives.getSubObjectiveCode());

            String successMessage = "Subobjective " + programObjectives.getSubObjectiveCode() + " of Objective " + programObjectives.getObjectiveCode() + " successfully made for Program-Course pair " + programObjectives.getProgramId() + " and " + programObjectives.getCourseId();
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    @PostMapping("/processEvaluationForm")
    public String processSectionForm(@ModelAttribute("EvaluationResults") EvaluationResults evaluationResults, RedirectAttributes redirectAttributes) {
        try {
            jdbcTemplate.update("INSERT INTO EvaluationResults (section_number, sub_objective_code, evaluation_method, students_met) VALUES (?, ?, ?, ?)",
                    evaluationResults.getSectionNumber(), evaluationResults.getSubObjectiveCode(), evaluationResults.getEvaluationMethod(), evaluationResults.getStudentsMet());

            String successMessage = "Successfully posted evaluation results for subobjective " + evaluationResults.getSubObjectiveCode() + " for section " + evaluationResults.getSectionNumber();
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

    private List<Programs> getPrograms() {
        String sql = "SELECT * FROM Programs";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Programs.builder()
                .programId(Long.valueOf(resultSet.getString("program_id")))
                .programName(resultSet.getString("program_name"))
                .departmentCode(resultSet.getString("department_code"))
                .headOfProgramId(resultSet.getString("head_of_program_id"))
                .build());
    }

    private List<Sections> getSections() {
        String sql = "SELECT * FROM Sections";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Sections.builder()
                .sectionNumber(Long.valueOf(resultSet.getString("section_number")))
                .courseId(resultSet.getString("course_id"))
                .semester(Semester.getByLabel(resultSet.getString("semester")))
                .facultyId(resultSet.getString("faculty_id"))
                .enrolledStudents(resultSet.getString("enrolled_students"))
                .year(resultSet.getString("year"))
                .build());
    }

    private List<LearningObjectives> getObjectives() {
        String sql = "SELECT * FROM LearningObjectives";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> LearningObjectives.builder()
                .objectiveCode(resultSet.getString("objective_code"))
                .programId(resultSet.getString("program_id"))
                .build());
    }

    private List<SubObjectives> getSubObjectives() {
        String sql = "SELECT * FROM SubObjectives";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> SubObjectives.builder()
                .subObjectiveCode(resultSet.getString("sub_objective_code"))
                .objectiveCode(resultSet.getString("objective_code"))
                .build());
    }

    private List<ProgramCourses> getProgramCourses() {
        String sql = "SELECT * FROM ProgramCourses";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> ProgramCourses.builder()
                .programId(resultSet.getString("program_id"))
                .courseId(resultSet.getString("course_id"))
                .build());
    }
}



