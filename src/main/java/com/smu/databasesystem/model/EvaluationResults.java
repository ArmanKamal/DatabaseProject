package com.smu.databasesystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationResults {
    private String section_course_id;
    private String program_course_id;
    private String programId;
    private String sectionNumber;
    private String semester;
    private String year;
    private String objectiveCode;
    private String subObjectiveCode;
    private String evaluationMethod;

    private String enrolledStudents;

    private String studentsMet;

    private String percentageOfStudentMet;

}
