package com.example.timetableapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.timetableapp.fragment.Weekday;
import com.example.timetableapp.model.WeekDays;

public class PageAdapter extends FragmentStatePagerAdapter
{
    private int number_of_tabs;
    private WeekDays weekDays;
    public PageAdapter(FragmentManager fm, int number_of_tabs, WeekDays weekDays)
    {
        super(fm,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.number_of_tabs = number_of_tabs;
        this.weekDays = weekDays;
    }
    @NonNull
    @Override
    public Fragment getItem(int i)
    {
        switch (i)
        {
            case 0:
                return new Weekday(weekDays.getWeekdaySubjectSlot("Monday"));
            case 1:
                return new Weekday(weekDays.getWeekdaySubjectSlot("Tuesday"));
            case 2:
                return new Weekday(weekDays.getWeekdaySubjectSlot("Wednesday"));
            case 3:
                return new Weekday(weekDays.getWeekdaySubjectSlot("Thursday"));
            case 4:
                return new Weekday(weekDays.getWeekdaySubjectSlot("Friday"));
            default:
                return new Weekday(weekDays.getWeekdaySubjectSlot("Monday"));
        }
    }

    @Override
    public int getCount()
    {
        return number_of_tabs;
    }
}