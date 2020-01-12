package com.example.timetableapp.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.timetableapp.R;
import com.example.timetableapp.fragment.Weekday;
import com.example.timetableapp.model.SubjectSlot;
import com.example.timetableapp.model.WeekDays;

import java.util.ArrayList;
import java.util.Arrays;

public class PageAdapter extends FragmentStatePagerAdapter
{
    private int number_of_tabs;
    private WeekDays weekDays;
    private Context context;
    public PageAdapter(FragmentManager fm, int number_of_tabs, WeekDays weekDays, Context context)
    {
        super(fm,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.number_of_tabs = number_of_tabs;
        this.weekDays = weekDays;
        this.context = context;
    }
    @NonNull
    @Override
    public Fragment getItem(int i)
    {
        ArrayList<String> time_slots = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.day_of_week)));
        String day = time_slots.get(i);
        return new Weekday(getSubjectsSLots(day));
    }

    ArrayList<SubjectSlot> getSubjectsSLots(String day)
    {
        ArrayList<SubjectSlot> ans = new ArrayList<>();

        try {

            ArrayList<String> time_slots = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.time_slot_list)));
            ArrayList<String> slots = weekDays.getWeekdaySubjectSlot(day);
            SubjectSlot temp;
            int end = 0;
            for (int i = 0; i < 10; i++) {
//                Log.d("i",Integer.toString(i));
                while (end < 10 && slots.get(i).equals(slots.get(end)))
                {
                    end++;
                }
                ans.add(new SubjectSlot(day,time_slots.get(end-1), time_slots.get(i), slots.get(i)));
                i = end - 1;
            }
        }
        catch (Exception e)
        {
            Log.d("Exc", e.toString() + "in PageAdapter");
        }
        return ans;
    }

    @Override
    public int getCount()
    {
        return number_of_tabs;
    }
}