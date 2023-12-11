package com.smu.databasesystem.controller;

import com.smu.databasesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();


    @GetMapping("/")
    public String showView(@ModelAttribute("Departments") Departments departments,
                           @ModelAttribute("Persons") Persons persons,
                           @ModelAttribute("Programs") Programs programs,
                           @ModelAttribute("FacultyMembers") FacultyMembers facultyMembers,
                           @ModelAttribute("Courses") Courses courses,
                           @ModelAttribute("Sections") Sections sections,
                           @ModelAttribute("LearningObjectives") LearningObjectives learningObjectives,
                           @ModelAttribute("SubObjectives") SubObjectives subObjectives,
                           @ModelAttribute("ProgramCourses") ProgramCourses programCourses,
                           @ModelAttribute("ProgramObjectives") ProgramObjectives programObjectives,
                           @ModelAttribute("EvaluationResults") EvaluationResults evaluationResults,
                           Model mav) {
        mav.addAttribute("departments", getDepartments());
        mav.addAttribute("departments", getDepartments());
        mav.addAttribute("courses", getCourses());
        mav.addAttribute("faculties", getFacultyMembers());
        mav.addAttribute("programs", getPrograms());
        mav.addAttribute("sections", getSections());
        mav.addAttribute("learningObjectives", getObjectives());
        mav.addAttribute("subObjectives", getSubObjectives());
        mav.addAttribute("programCourses", getProgramCourses());
        mav.addAttribute("programObjectives", getProgramObjectives());

        return "index";
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
            jdbcTemplate.update("INSERT INTO FacultyMembers (faculty_id,faculty_name, email_address, faculty_rank, department_code) VALUES (?,?, ?, ?, ?)",
                    facultyMember.getFacultyId(), facultyMember.getFacultyName(), facultyMember.getEmailAddress(), facultyMember.getFacultyRank().name(), facultyMember.getDepartmentCode());

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
            jdbcTemplate.update("INSERT INTO Sections (section_number, course_id, semester, faculty_id, enrolled_students, year) VALUES (?, ?, ?, ?,?, ?)",
                    section.getSectionNumber(), section.getCourseId(), section.getSemester().name(), section.getFacultyId(), section.getEnrolledStudents(), section.getYear());

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
            jdbcTemplate.update("INSERT INTO SubObjectives (sub_objective_code, objective_code, description) VALUES (?, ?, ?)",
                    subObjectives.getSubObjectiveCode(), subObjectives.getObjectiveCode(), subObjectives.getDescription());

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
            int rowCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM Programs P JOIN Courses C ON P.department_code = C.department_code WHERE P.program_id = ? AND C.course_id = ?",
                    Integer.class,
                    programCourses.getProgramId(),
                    programCourses.getCourseId()
            );

            if (rowCount > 0) {
                jdbcTemplate.update(
                        "INSERT INTO ProgramCourses (program_id, course_id) VALUES (?, ?)",
                        programCourses.getProgramId(),
                        programCourses.getCourseId()
                );

                String successMessage = "Program-Course pair successfully made for program ID " + programCourses.getProgramId() + " and course ID " + programCourses.getCourseId();
                redirectAttributes.addFlashAttribute("successMessage", successMessage);
            } else {
                String failureMessage = "Program and course do not belong to the same department.";
                redirectAttributes.addFlashAttribute("failureMessage", failureMessage);
            }
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }

    @PostMapping("/processProgramObjectivesForm")
    public String processObjectiveForm(@ModelAttribute("ProgramObjectives") ProgramObjectives programObjectives, RedirectAttributes redirectAttributes) {
        try {
            String[] parts = programObjectives.getSubObjectiveCode().split(",");
            String programmingSubObjective = parts[0];
            String programmingObjective = parts[1];

            int rowCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM SubObjectives so JOIN LearningObjectives lo ON so.objective_code=lo.objective_code WHERE lo.program_id = ? AND lo.objective_code= ?",
                    Integer.class,
                    programObjectives.getProgramId(),
                    programmingObjective
            );
            System.out.println("Row count"+rowCount+"And Program id:"+programObjectives.getProgramId());
            if (rowCount > 0) {
                jdbcTemplate.update("INSERT INTO ProgramObjectives (program_id, course_id, sub_objective_code,objective_code) VALUES (?, ?, ?,?)",
                        programObjectives.getProgramId(), programObjectives.getCourseId(),programmingSubObjective ,programmingObjective);

                String successMessage = "Subobjective " + programObjectives.getSubObjectiveCode() + " of Objective " + programObjectives.getObjectiveCode() + " successfully made for Program-Course pair " + programObjectives.getProgramId() + " and " + programObjectives.getCourseId();
                redirectAttributes.addFlashAttribute("successMessage", successMessage);

            } else {
                String failureMessage = "SubObjective doesnot belong to : "+programObjectives.getProgramId();
                redirectAttributes.addFlashAttribute("failureMessage", failureMessage);
            }
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return "redirect:/";
    }


    @PostMapping("/processEvaluationForm")
    public String processSectionForm(@ModelAttribute("EvaluationResults") EvaluationResults evaluationResults, RedirectAttributes redirectAttributes) {
        try {
            String[] parts = evaluationResults.getSectionNumber().split(",");
            String couresId = parts[0];
            String year = parts[1];
            String semester = parts[2];
            String sectionNumber = parts[3];

            String[] programObjectiveParts = evaluationResults.getSubObjectiveCode().split(",");
            String programId = programObjectiveParts[0];
            String subObjectiveCode = programObjectiveParts[1];

            int rowCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM ProgramObjectives WHERE course_id = ?",
                    Integer.class,
                   couresId
            );
            if(rowCount>0){
                jdbcTemplate.update("INSERT INTO EvaluationResults (section_course_id,program_course_id,section_number,program_id, semester,sub_objective_code, evaluation_method, students_met,year) VALUES (?, ?, ?, ?,?,?,?,?,?)",
                        couresId,couresId,sectionNumber,programId,semester,subObjectiveCode,evaluationResults.getEvaluationMethod(), evaluationResults.getStudentsMet(),year);

                String successMessage = "Successfully posted evaluation results for subobjective " + evaluationResults.getSubObjectiveCode() + " for section " + evaluationResults.getSectionNumber();
                redirectAttributes.addFlashAttribute("successMessage", successMessage);
            }
            else{
                String failure = "Course is not associated with the Subobjective";
                redirectAttributes.addFlashAttribute("failureMessage", failure);
            }

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
        String sql = "SELECT pc.program_id,pc.course_id,c.course_title,p.program_name FROM ProgramCourses pc JOIN Courses c ON  pc.course_id=c.course_id JOIN Programs p ON pc.program_id=p.program_id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> ProgramCourses.builder()
                .programId(resultSet.getString("program_id"))
                .courseId(resultSet.getString("course_id"))
                .courseName(resultSet.getString("course_title"))
                .programName(resultSet.getString("program_name"))
                .build());
    }

    private List<ProgramObjectives> getProgramObjectives() {
        String sql = "SELECT * FROM ProgramObjectives";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> ProgramObjectives.builder()
                .programId(resultSet.getString("program_id"))
                .courseId(resultSet.getString("course_id"))
                .subObjectiveCode(resultSet.getString("sub_objective_code"))
                .objectiveCode(resultSet.getString("objective_code"))
                .build());
    }


//    private List<ProgramObjectives> getProgramObjectives() {
//        String sql = "SELECT DISTINCT  pc.program_id, pc.course_id, po.objective_code, po.sub_objective_code, p.program_name" +
//                " FROM ProgramCourses pc" +
//                " LEFT JOIN ProgramObjectives po ON pc.program_id = po.program_id" +
//                " LEFT JOIN Programs p ON pc.program_id = p.program_id" +
//                " GROUP BY pc.program_id, po.course_id, po.objective_code, po.sub_objective_code, p.program_name;";
//        return jdbcTemplate.query(sql, (resultSet, rowNum) -> ProgramObjectives.builder()
//                .programId(resultSet.getString("program_id"))
//                .courseId(resultSet.getString("course_id"))
//                .objectiveCode(resultSet.getString("objective_code"))
//                .subObjectiveCode(resultSet.getString("sub_objective_code"))
//                .programName(resultSet.getString("program_name"))
//                .build());
//    }

}



