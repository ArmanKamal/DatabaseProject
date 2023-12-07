package com.smu.databasesystem.model.query;

import com.smu.databasesystem.model.FacultyRank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDetails {
    private String programId;
    private String programName;
    private String facultyId;
    private String facultyName;
    private String facultyEmail;

    private FacultyRank facultyRank;

    private String programInCharge;
}
