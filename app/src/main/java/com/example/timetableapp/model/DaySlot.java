package com.example.timetableapp.model;

import android.widget.ImageView;
import android.widget.TextView;

public class DaySlot
{
    private String start_time;
    private TextView status;

    public DaySlot(){}
    public DaySlot(String start_time, TextView status) {
        this.start_time = start_time;
        this.status = status;
    }

    public TextView getStatus() {
        return status;
    }

    public void setStatus(TextView status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
