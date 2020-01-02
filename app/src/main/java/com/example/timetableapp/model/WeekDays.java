package com.example.timetableapp.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class WeekDays implements Serializable {

    private HashMap<String, WeekDay> weekDayHashMap = new HashMap<>();

    public WeekDays(){
        weekDayHashMap.put("Monday", new WeekDay());
        weekDayHashMap.put("Tuesday", new WeekDay());
        weekDayHashMap.put("Wednesday", new WeekDay());
        weekDayHashMap.put("Thursday", new WeekDay());
        weekDayHashMap.put("Friday", new WeekDay());
    }

    public HashMap<String, WeekDay> getWeekDayHashMap() {
        return weekDayHashMap;
    }

    public void addWeekdaySubjectSlot(String weekday, SubjectSlot subjectSlot) {
        Objects.requireNonNull(weekDayHashMap.get(weekday)).subjectSlots.add(subjectSlot);
    }

    public void clearWeekdaySubjectSlot()
    {
        Objects.requireNonNull(weekDayHashMap.get("Monday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Tuesday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Wednesday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Thursday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Friday")).subjectSlots.clear();


    }

    public ArrayList<SubjectSlot> getWeekdaySubjectSlot(String weekday){
        if(weekDayHashMap.get(weekday)!=null)
            return weekDayHashMap.get(weekday).getSubjectSlots();
        else
            return new ArrayList<>();
    }

    public class WeekDay implements Serializable {
        private ArrayList<SubjectSlot> subjectSlots = new ArrayList<>();

        public ArrayList<SubjectSlot> getSubjectSlots() {
            return subjectSlots;
        }
    }
}


