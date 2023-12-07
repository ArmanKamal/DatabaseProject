package com.smu.databasesystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courses {
    private String courseId;
    private String courseTitle;
    private String courseDescription;
    private String departmentCode;
}
