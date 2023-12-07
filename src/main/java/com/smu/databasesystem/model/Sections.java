package com.smu.databasesystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sections {
    private Long sectionNumber;
    private String courseId;
    private Semester semester;
    private String facultyId;
    private String enrolledStudents;

    private String year;
}
