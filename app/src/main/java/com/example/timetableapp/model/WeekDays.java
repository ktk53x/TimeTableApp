package com.example.timetableapp.model;


import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class WeekDays implements Serializable {

    private HashMap<String, ArrayList<String>> weekDayHashMap = new HashMap<>();

    //Monday -
    //          0: CS101
    //          1: Break
    //Tuesday...


    ArrayList<String> initializeArray()
    {
        ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                temp.add("Break");
            }
            return temp;
    }

    public WeekDays(){

        weekDayHashMap.put("Monday", initializeArray());
        weekDayHashMap.put("Tuesday", initializeArray());
        weekDayHashMap.put("Wednesday", initializeArray());
        weekDayHashMap.put("Thursday", initializeArray());
        weekDayHashMap.put("Friday", initializeArray());
    }

    public HashMap<String, ArrayList<String>> getWeekDayHashMap() {
        return weekDayHashMap;
    }

    public void addWeekdaySubjectSlot(String weekday, String start_time, String end_time, String subject_code) {
        int start, end;
        start = Integer.parseInt(start_time.substring(0, 2));
        end = Integer.parseInt(end_time.substring(0, 2));
        for(int i = start-8; i < end-8; i++)
            Objects.requireNonNull(weekDayHashMap.get(weekday)).set(i,subject_code);

    }

    public void clearWeekdaySubjectSlot()
    {
        Objects.requireNonNull(weekDayHashMap.get("Monday")).clear();
        Objects.requireNonNull(weekDayHashMap.get("Tuesday")).clear();
        Objects.requireNonNull(weekDayHashMap.get("Wednesday")).clear();
        Objects.requireNonNull(weekDayHashMap.get("Thursday")).clear();
        Objects.requireNonNull(weekDayHashMap.get("Friday")).clear();


    }

    public ArrayList<String> getWeekdaySubjectSlot(String weekday){
        if(weekDayHashMap.get(weekday)!=null)
            return weekDayHashMap.get(weekday);
        else
            return new ArrayList<String>(new ArrayList<String>());
    }
//
//    public class ArrayList<String> implements Serializable {
//        private ArrayList<String> subjectSlots;
//        public ArrayList<String>(){
//            ArrayList<String> temp = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                temp.add("Break");
//            }
//            this.subjectSlots = temp;
//
//        }
//
//
//        public ArrayList<String>(ArrayList<String> subjectSlots) {
//            this.subjectSlots = subjectSlots;
//        }
//
//        public ArrayList<String> getSubjectSlots() {
//            return subjectSlots;
//        }
//
//        public void setSubjectSlots(ArrayList<String> subjectSlots) {
//            this.subjectSlots = subjectSlots;
//        }
//    }
}


