package com.example.timetableapp.model;

import android.widget.ImageView;

public class DaySlot
{
    private String start_time;
    private ImageView status;

    public DaySlot(String start_time, ImageView status) {
        this.start_time = start_time;
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public ImageView getStatus() {
        return status;
    }

    public void setStatus(ImageView status) {
        this.status = status;
    }
}
