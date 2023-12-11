package com.smu.databasesystem.controller;

import com.smu.databasesystem.model.*;
import com.smu.databasesystem.model.query.CourseDetails;
import com.smu.databasesystem.model.query.DepartmentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ModelAndView showDepartmentPage(@ModelAttribute("Departments") Departments departments,
                                           @RequestParam(name = "departmentCode", required = false) String departmentCode,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("department");

        try {
            mav.addObject("departments", getDepartments());
            if (departmentCode != null && !departmentCode.isEmpty()) {

                model.addAttribute("programDetails", getProgramsForDepartment(departmentCode));
                model.addAttribute("faculties", getFacultyForDepartment(departmentCode));
                model.addAttribute("departmentMessage", departmentCode);

            }
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);

        }
        return mav;
    }

    @GetMapping("/programs")
    public ModelAndView showProgramPage(@ModelAttribute("Programs") Programs programs,
                                        @RequestParam(name = "programId", required = false) String programId,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("program");
        try {
            mav.addObject("programs", getPrograms());
            if (programId != null && !programId.isEmpty()) {
                System.out.println("came here" + programId);

                // Get courses for the program
                List<ProgramObjectives> programObjectives = getProgramObjective(programId);
                model.addAttribute("programObjectives", programObjectives);

                List<CourseDetails> courses = getCoursesForProgram(programId);
                model.addAttribute("courses", courses);

                // Get all objectives for the program
                List<CourseDetails> objectives = getObjectivesForProgram(programId);
                System.out.println("Get Objectives" + objectives.get(0));
                model.addAttribute("objectives", objectives);

                model.addAttribute("programMessage", programId);

            }
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return mav;
    }

    @GetMapping("/evaluations")
    public ModelAndView showEvaluationPage(@ModelAttribute("EvaluationResults") EvaluationResults evaluationResults,
                                           @RequestParam(name = "semester", required = false) String semester,
                                           @RequestParam(name = "programId", required = false) String program,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("evaluation");
        try {
            List<String> distinctSemesters = getDistinctSemesters();
            List<Programs> distinctPrograms = getDistinctPrograms();

            mav.addObject("distinctSemesters", distinctSemesters);
            mav.addObject("distinctPrograms", distinctPrograms);
            if (semester != null && !semester.isEmpty() && program != null && !program.isEmpty()) {
//                List<EvaluationResults> evaluationResultsList = getEvaluationResults(semester, program);
//                model.addAttribute("evaluationResultsList", evaluationResultsList);


            }
        } catch (Exception e) {
            String failure = "Failed because of " + e;
            redirectAttributes.addFlashAttribute("failureMessage", failure);
        }
        return mav;
    }

    private List<CourseDetails> getCoursesForProgram(String programId) {
        String sql = "SELECT c.course_id, c.course_title, po.objective_code, po.sub_objective_code " +
                "FROM Courses c " +
                "JOIN ProgramCourses pc ON c.course_id = pc.course_id " +
                "LEFT JOIN ProgramObjectives po ON pc.program_id = po.program_id AND c.course_id = po.course_id " +
                "WHERE pc.program_id = ?";

        return jdbcTemplate.query(sql, new Object[]{programId}, (resultSet, rowNum) -> CourseDetails.builder()
                .courseId(resultSet.getString("course_id"))
                .courseTitle(resultSet.getString("course_title"))
                .objectiveCode(resultSet.getString("objective_code"))
                .subObjectiveCode(String.valueOf(resultSet.getInt("sub_objective_code")))
                .build());
    }

    private List<CourseDetails> getObjectivesForProgram(String programId) {
        String sql = "SELECT lo.objective_code, lo.program_id,so.sub_objective_code,so.description FROM LearningObjectives lo LEFT JOIN SubObjectives so ON lo.objective_code = so.objective_code" +
        " WHERE program_id = ?";

        return jdbcTemplate.query(sql, new Object[]{programId}, (resultSet, rowNum) -> CourseDetails.builder()
                .objectiveCode(resultSet.getString("objective_code"))
                .programId(resultSet.getString("program_id"))
                .build());
    }

    private List<Programs> getPrograms() {
        String sql = "SELECT * FROM Programs";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Programs.builder()
                .programId(Long.valueOf(resultSet.getString("program_id")))
                .programName(resultSet.getString("program_name"))
                .build());
    }

    private List<Departments> getDepartments() {
        String sql = "SELECT * FROM Departments";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Departments.builder()
                .departmentCode(resultSet.getString("department_code"))
                .departmentName(resultSet.getString("department_name"))
                .build());
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

//    public List<EvaluationResults> getEvaluationResults(
//            @RequestParam("semester") String semester,
//            @RequestParam("program") String program) {
//
//        String query = "SELECT er.section_number, er.sub_objective_code, er.evaluation_method, er.students_met " +
//                "FROM EvaluationResults er " +
//                "JOIN Sections s ON er.section_number = s.section_number " +
//                "JOIN ProgramCourses c ON s.course_id = c.course_id " +
//                "WHERE s.semester = ? AND c.program_id = ?";
//
//        return jdbcTemplate.query(query,
//                (resultSet, rowNum) -> new EvaluationResults(
//                        resultSet.getString("section_number"),
//                        resultSet.getString("sub_objective_code"),
//                        resultSet.getString("evaluation_method"),
//                        resultSet.getString("students_met"),
//                        resultSet.getString("semester"),
//                        resultSet.getString("year"),
//                        resultSet.getString("program_id")
//                ),
//                semester, program);
//
//    }

    public List<String> getDistinctSemesters() {
        String query = "SELECT DISTINCT semester FROM Sections";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public List<Programs> getDistinctPrograms() {
        String query = "SELECT DISTINCT program_id, program_name FROM Programs";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
                Programs.builder()
                        .programId(resultSet.getLong("program_id"))
                        .programName(resultSet.getString("program_name"))
                        .build());
    }

    public List<ProgramObjectives> getProgramObjective(String programId) {
        String query = "SELECT * FROM ProgramObjectives WHERE program_id=?";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
                ProgramObjectives.builder()
                        .programId(resultSet.getString("program_id"))
                        .courseId(resultSet.getString("course_id"))
                        .objectiveCode(resultSet.getString("objective_code"))
                        .subObjectiveCode(resultSet.getString("sub_objective_code"))
                        .build(),programId);
    }
//    SELECT *,er.students_met/s.enrolled_students FROM university.sections s
//    JOIN university.programobjectives pc ON s.course_id=pc.course_id
//    JOIN university.evaluationresults er ON s.section_number=er.section_number
//    WHERE (year='2013' AND (semester = 'Fall' OR semester = 'Summer')) OR year='2014' AND semester = 'Spring'
}
