package com.example.timetableapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.timetableapp.Interfaces.send_data;
import com.example.timetableapp.adapters.DaySlotAdapter;
import com.example.timetableapp.adapters.SubjectSlotAdapter;
import com.example.timetableapp.model.DaySlot;
import com.example.timetableapp.model.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EditTimeTableActivity extends AppCompatActivity{

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_time_table);
            RecyclerView recyclerView = findViewById(R.id.monday_slots);
            HashMap<String, Subject> subjects = (HashMap<String, Subject>)getIntent().getSerializableExtra("Subject");
            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(EditTimeTableActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            ArrayList<String> slot_time = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.time_slot_list)));
            final ArrayList<DaySlot> slots = new ArrayList<>();
            for(int i = 0; i < slot_time.size(); i++){
                ImageView temp = new ImageView(this);
                temp.setImageResource(R.drawable.ic_add_circle_black_24dp);
                slots.add(new DaySlot(slot_time.get(i),temp));

            }
            DaySlotAdapter adapter = new DaySlotAdapter(this, slots, this, subjects);
            recyclerView.setAdapter(adapter);

        }
        catch (Exception e)
        {
            Log.d("Exc",e.toString() + " in EditTimeTable");
        }

    }
}
