package com.example.timetableapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class BTech implements Serializable {

    private ArrayList<Subject> subject = new ArrayList<>();
    private ArrayList<SubjectSlot> subjectSlot = new ArrayList<>();

    public ArrayList<Subject> getSubject() {
        return subject;
    }

    public void addSubject(Subject subject) {
        this.subject.add(subject);
    }

    public void addSubjectSlot(SubjectSlot subjectSlot) {
        this.subjectSlot.add(subjectSlot);
    }

    public ArrayList<SubjectSlot> getSubjectSlot() {
        return subjectSlot;
    }
}

