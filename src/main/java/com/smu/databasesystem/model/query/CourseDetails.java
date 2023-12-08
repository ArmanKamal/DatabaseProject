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
public class CourseDetails {

    private String programId;
    private String courseId;
    private String courseTitle;
    private String objectiveCode;
    private String subObjectiveCode;
    private String description;
}
