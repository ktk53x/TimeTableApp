package com.example.timetableapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.timetableapp.adapters.PageAdapter;
import com.example.timetableapp.R;
import com.example.timetableapp.model.WeekDays;
import com.google.android.material.tabs.TabLayout;

public class ShowTimeTable extends AppCompatActivity
{
    private static String WEEKDAYS = "Weekday";

    public static Intent getInstance(Context context, WeekDays weekDays){
        Intent intent = new Intent(context, ShowTimeTable.class);
        intent.putExtra(WEEKDAYS, weekDays);
        return intent;
    }

    private TextView details;
    private WeekDays weekDays;
    public final String TAG = "Kartikeya";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time_table);
        weekDays = (WeekDays) getIntent().getSerializableExtra(WEEKDAYS);
        String[] days = getResources().getStringArray(R.array.day_of_week);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        for (String day : days)
            tabLayout.addTab(tabLayout.newTab().setText(day));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), weekDays);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }
}
