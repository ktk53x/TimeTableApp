package com.example.timetableapp.model;


import java.io.Serializable;

public class Subject implements Serializable {
    private String courseCode;
    private String professor;
    private String venue;
    private String courseName;
    private Boolean labCourse;
    private Boolean HSS;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Boolean getLabCourse() {
        return labCourse;
    }

    public Subject(String courseCode, String professor, String venue, String courseName, Boolean labCourse, Boolean HSS) {
        this.courseCode = courseCode;
        this.professor = professor;
        this.venue = venue;
        this.courseName = courseName;
        this.labCourse = labCourse;
        this.HSS = HSS;
    }

    public void setLabCourse(Boolean labCourse) {
        this.labCourse = labCourse;
    }

    public Boolean getHSS() {
        return HSS;
    }

    public void setHSS(Boolean HSS) {
        this.HSS = HSS;
    }


    public Subject(){}


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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
