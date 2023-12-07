package com.smu.databasesystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramObjectives {
    private String programId;
    private String courseId;
    private String objectiveCode;
    private String subObjectiveCode;
}
