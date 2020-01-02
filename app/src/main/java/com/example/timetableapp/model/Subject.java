package com.example.timetableapp.model;


import java.io.Serializable;

public class Subject implements Serializable {
    private String branchYear;
    private String courseName;
    private String professor;
    private String venue;

    public String getBranchYear() {
        return branchYear;
    }

    public void setBranchYear(String branchYear) {
        this.branchYear = branchYear;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}