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
                // Get courses for the program
                List<ProgramObjectives> programObjectives = getProgramObjective(programId);
                model.addAttribute("programObjectives", programObjectives);

                List<CourseDetails> courses = getCoursesForProgram(programId);
                model.addAttribute("courses", courses);

                // Get all objectives for the program
                List<CourseDetails> objectives = getObjectivesForProgram(programId);
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
                                           @RequestParam(name = "yearId", required = false) String year,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("evaluation");
        try {
            List<String> distinctSemesters = getDistinctSemesters();
            List<Programs> distinctPrograms = getDistinctPrograms();

            mav.addObject("distinctSemesters", distinctSemesters);
            mav.addObject("distinctPrograms", distinctPrograms);
            if (semester != null && !semester.isEmpty() && program != null && !program.isEmpty()) {
                List<EvaluationResults> evaluationResultsList = getEvaluationResults(semester, program);
                model.addAttribute("evaluationResultsList", evaluationResultsList);
                model.addAttribute("selectedSem", semester);
                model.addAttribute("selectedPro", getProgramById(program).get(0).getProgramName());

            }
            if (year != null && !year.isEmpty()) {
                List<EvaluationResults> evaluationResultsList = getEvaluationResultBasedOnYear(year);
                model.addAttribute("evaluationResultsYearList", evaluationResultsList);
                model.addAttribute("selectedYear", year);
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

    private List<Programs> getProgramById(String programId) {
        String sql = "SELECT * FROM Programs WHERE program_id=?";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Programs.builder()
                .programId(Long.valueOf(resultSet.getString("program_id")))
                .programName(resultSet.getString("program_name"))
                .build(), programId);
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

    public List<EvaluationResults> getEvaluationResults(
            @RequestParam("semester") String semester,
            @RequestParam("program") String program) {

        String query = "SELECT S.section_number, S.course_id, COALESCE(E.program_id, 'Not Found') AS program_id, " +
                "S.semester, COALESCE(E.sub_objective_code, 'Not Found') AS sub_objective_code, " +
                "COALESCE(E.evaluation_method, 'Not Found') AS evaluation_method, " +
                "COALESCE(E.students_met, 'Not Found') AS students_met, COALESCE(E.year, 'Not Found') AS year " +
                " FROM Sections S " +
                " LEFT JOIN EvaluationResults E ON S.section_number = E.section_number " +
                " AND S.semester = E.semester AND S.course_id = E.section_course_id AND E.program_id = ? " +
                " WHERE S.semester = ? AND E.program_id = ? " +
                " ORDER BY section_number, course_id, sub_objective_code, evaluation_method, year";
        ;

        return jdbcTemplate.query(query,
                (resultSet, rowNum) -> EvaluationResults.builder()
                        .evaluationMethod(resultSet.getString("evaluation_method"))
                        .sectionNumber(resultSet.getString("section_number"))
                        .subObjectiveCode(resultSet.getString("sub_objective_code"))
                        .studentsMet(resultSet.getString("students_met"))
                        .program_course_id(resultSet.getString("course_id"))
                        .year(resultSet.getString("year")).build(), program, semester, program);

    }

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
                        .build(), programId);
    }

    public List<EvaluationResults> getEvaluationResultBasedOnYear(String year) {
        String sql = "SELECT " +
                "po.objective_code AS program_objective_code, " +
                "po.sub_objective_code AS program_sub_objective_code, " +
                "s.course_id AS section_course_id, " +
                "s.section_number, " +
                "s.semester, " +
                "s.year, " +
                "er.evaluation_method, " +
                "s.enrolled_students, " +
                "er.students_met, " +
                "LEAST((er.students_met / NULLIF(s.enrolled_students, 0)) * 100, 100) AS percentage_students_met " +
                "FROM " +
                "university.programobjectives po " +
                "JOIN " +
                "university.programcourses pc ON po.program_id = pc.program_id " +
                "JOIN " +
                "university.sections s ON pc.course_id = s.course_id " +
                "JOIN " +
                "university.evaluationresults er ON s.section_number = er.section_number AND s.semester = er.semester AND s.year = er.year " +
                "WHERE (s.year = ? AND (s.semester = 'Fall' OR s.semester = 'Summer')) OR (s.year = ? AND s.semester = 'Spring')";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                EvaluationResults.builder()
                        .section_course_id(rs.getString("section_course_id"))
                        .program_course_id(rs.getString("section_course_id"))
                        .sectionNumber(rs.getString("section_number"))
                        .semester(rs.getString("semester"))
                        .year(rs.getString("year"))
                        .objectiveCode(rs.getString("program_objective_code"))
                        .subObjectiveCode(rs.getString("program_sub_objective_code"))
                        .evaluationMethod(rs.getString("evaluation_method"))
                        .enrolledStudents(rs.getString("enrolled_students"))
                        .studentsMet(rs.getString("students_met"))
                        .percentageOfStudentMet(rs.getString("percentage_students_met"))
                        .build(), year, String.valueOf(Integer.parseInt(year )+1));
    }
}
