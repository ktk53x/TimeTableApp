package com.example.timetableapp.model;


import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class WeekDays implements Serializable {

    private HashMap<String, WeekDay> weekDayHashMap = new HashMap<>();

    //Monday -
    //          0: CS101
    //          1: Break
    //Tuesday...

    public WeekDays(){
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            temp.add("Break");
        }
        weekDayHashMap.put("Monday", new WeekDay(temp));
        weekDayHashMap.put("Tuesday", new WeekDay(temp));
        weekDayHashMap.put("Wednesday", new WeekDay(temp));
        weekDayHashMap.put("Thursday", new WeekDay(temp));
        weekDayHashMap.put("Friday", new WeekDay(temp));
    }

    public HashMap<String, WeekDay> getWeekDayHashMap() {
        return weekDayHashMap;
    }

    public void addWeekdaySubjectSlot(String weekday, String start_time, String end_time, String subject_code) {
        int start, end;
        start = Integer.parseInt(start_time.substring(0, 2));
        end = Integer.parseInt(end_time.substring(0, 2));
        for(int i = start-8; i < end-8; i++)
            Objects.requireNonNull(weekDayHashMap.get(weekday)).subjectSlots.set(i,subject_code);

    }

    public void clearWeekdaySubjectSlot()
    {
        Objects.requireNonNull(weekDayHashMap.get("Monday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Tuesday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Wednesday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Thursday")).subjectSlots.clear();
        Objects.requireNonNull(weekDayHashMap.get("Friday")).subjectSlots.clear();


    }

    public WeekDay getWeekdaySubjectSlot(String weekday){
        if(weekDayHashMap.get(weekday)!=null)
            return weekDayHashMap.get(weekday);
        else
            return new WeekDay(new ArrayList<String>());
    }

    public class WeekDay implements Serializable {
        private ArrayList<String> subjectSlots;

        public WeekDay(ArrayList<String> subjectSlots) {
            this.subjectSlots = subjectSlots;
        }

        public ArrayList<String> getSubjectSlots() {
            return subjectSlots;
        }

        public void setSubjectSlots(ArrayList<String> subjectSlots) {
            this.subjectSlots = subjectSlots;
        }
    }
}


