package com.challenge.interview.calendar.model;

public enum Role {
    CANDIDATE("candidate"),
    INTERVIEWER("interviewer");


    private String name;
    private Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
