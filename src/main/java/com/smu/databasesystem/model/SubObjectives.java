package com.smu.databasesystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubObjectives {
    private String subObjectiveCode;
    private  String objectiveCode;
    private String description;
}
