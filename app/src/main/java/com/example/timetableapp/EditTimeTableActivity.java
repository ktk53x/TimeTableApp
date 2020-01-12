package com.example.timetableapp;

import androidx.annotation.NonNull;
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
import android.widget.TextView;

import com.example.timetableapp.Interfaces.send_data;
import com.example.timetableapp.adapters.DaySlotAdapter;
import com.example.timetableapp.adapters.SubjectSlotAdapter;
import com.example.timetableapp.model.DaySlot;
import com.example.timetableapp.model.Subject;
import com.example.timetableapp.model.SubjectSlot;
import com.example.timetableapp.model.WeekDays;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.min;
import static java.lang.StrictMath.max;

public class EditTimeTableActivity extends AppCompatActivity{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ArrayList<SubjectSlot>> fin = new ArrayList<>();
    HashMap<String, Subject> subjects;
    String yearBranch;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_time_table);
            final ArrayList<RecyclerView> recyclerViews = new ArrayList<>();
            ArrayList<String> ids  = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.day_ids)));
            for(String id : ids) {
                RecyclerView recyclerView;
                recyclerView = findViewById(getResources().getIdentifier(id,
                        "id", getPackageName()));
             recyclerViews.add(recyclerView);
            }
            subjects = (HashMap<String, Subject>)getIntent().getSerializableExtra("Subject");
            yearBranch = getIntent().getStringExtra("yearBranch");

            for(RecyclerView recyclerView : recyclerViews){
                LinearLayoutManager horizontalLayoutManager
                        = new LinearLayoutManager(EditTimeTableActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManager);
            }

            for (int i = 0; i < recyclerViews.size(); i++) {
                fin.add(new ArrayList<SubjectSlot>());
            }

            db
                    .collection("timetable")
                    .document("BTech")
                    .collection(yearBranch+"SLOTS")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if(task.isSuccessful()) {
                                WeekDays weekDays = new WeekDays();
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    weekDays = document.toObject(weekDays.getClass());

                                }


                                ArrayList<String> time_slots = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.time_slot_list)));
                                ArrayList<String> days = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.day_of_week)));
//                                for(String day : days)
                                for (int j = 0; j < recyclerViews.size(); j++) {
                                    ArrayList<SubjectSlot> ans = new ArrayList<>();
                                    ArrayList<String> slots = weekDays.getWeekdaySubjectSlot(days.get(j));
                                    SubjectSlot temp;
                                    int end = 0;
                                    for (int i = 0; i>=0 && i < 10; i++) {
                                        while (end >=0 && end < 10 && !(slots.get(i).equals("Break")) && slots.get(i).equals(slots.get(end))) {
                                            end++;
                                        }
                                        ans.add(new SubjectSlot(days.get(j), time_slots.get(max(end - 1, i)), time_slots.get(i), slots.get(i)));
                                        i = max(end - 1,i);
                                    }
                                    fin.set(j, ans);
                                }
                                for (int j = 0; j < recyclerViews.size(); j++) {
                                    ArrayList<String> slot_time = new ArrayList<>();
                                    for (SubjectSlot time : fin.get(j)) {
                                        slot_time.add(time.getSlotStart());
                                    }
                                    final ArrayList<DaySlot> slots = new ArrayList<>();
                                    for (int i = 0; i < slot_time.size(); i++) {
                                        TextView temp = new TextView(getApplicationContext());
                                        temp.setBackground(getResources().getDrawable(R.drawable.ic_add_circle_black_24dp));
                                        slots.add(new DaySlot(slot_time.get(i), temp));

                                    }
                                    DaySlotAdapter adapter = new DaySlotAdapter(getApplicationContext(), slots, EditTimeTableActivity.this, subjects, yearBranch);
                                    recyclerViews.get(j).setAdapter(adapter);
                                }
                            }
                            else
                                {
                                    //TODO: exception handling
                                }
                        }
                    });


        }
        catch (Exception e)
        {
            Log.d("Exc",e.toString() + " in EditTimeTable");
        }

    }
}
