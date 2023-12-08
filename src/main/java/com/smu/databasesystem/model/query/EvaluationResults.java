package com.smu.databasesystem.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationResults {
    private Long sectionNumber;
    private String subObjectiveCode;
    private String evaluationMethod;
    private Long studentsMet;
}
