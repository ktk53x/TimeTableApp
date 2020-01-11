package com.example.timetableapp.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

public class SubjectSlot implements Serializable{
    private String dayOfWeek;
    private String slotEnd;
    private String slotStart;
    private String subject;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSlotEnd() {
        return slotEnd;
    }

    public void setSlotEnd(String slotEnd) {
        this.slotEnd = slotEnd;
    }

    public String getSlotStart() {
        return slotStart;
    }

    public void setSlotStart(String slotStart) {
        this.slotStart = slotStart;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    public static class SortByTime implements Comparator<SubjectSlot>
    {

        @Override
        public int compare(SubjectSlot subjectSlot, SubjectSlot t1) {
            Integer x = Integer.parseInt(before(subjectSlot.getSlotStart(),":")) - Integer.parseInt(before(t1.getSlotStart(),":"));
            Log.d("K", Integer.toString(x));
            return x;
        }
    }
}
