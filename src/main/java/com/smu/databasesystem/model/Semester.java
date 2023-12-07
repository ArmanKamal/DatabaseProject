package com.smu.databasesystem.model;

public enum Semester {
    FALL("Fall"),
    SPRING("Spring"),
    SUMMER("Summer");

    public final String label;

    private Semester(String label) {
        this.label = label;
    }

    public static Semester getByLabel(String label) {
        for (Semester semester : values()) {
            if (semester.label.equalsIgnoreCase(label)) {
                return semester;
            }
        }
        throw new IllegalArgumentException("No constant with label " + label + " found");
    }
}
