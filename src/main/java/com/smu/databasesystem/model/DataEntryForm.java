package com.smu.databasesystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataEntryForm {

    private String departmentName;
    private String facultyName;
    private String programName;
    private String courseName;
    private String sectionName;
    private String learningObjectives;
    private List<String> programCourses;
    private List<String> courseProgramObjectives;
    private String evaluationResults;

}
