package com.smu.databasesystem.model;


import lombok.Getter;
import lombok.Setter;

@Getter
public enum FacultyRank {
    FULL("full"),
    ASSOCIATE("associate"),
    ASSISTANT("assistant"),
    ADJUNCT("adjunct");

    public final String label;

    private FacultyRank(String label) {
        this.label = label;
    }

    public static FacultyRank getByLabel(String label) {
        for (FacultyRank rank : values()) {
            if (rank.label.equalsIgnoreCase(label)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("No constant with label " + label + " found");
    }
}