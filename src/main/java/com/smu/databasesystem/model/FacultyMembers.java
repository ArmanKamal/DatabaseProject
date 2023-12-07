package com.smu.databasesystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyMembers {
    private String facultyId;
    private String facultyName;
    private String emailAddress;
    private FacultyRank facultyRank;
    private String departmentCode;
}
