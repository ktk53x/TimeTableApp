package com.example.timetableapp.fragment.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.timetableapp.EditTimeTableActivity;
import com.example.timetableapp.Interfaces.send_data;
import com.example.timetableapp.R;
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
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.startActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSlot extends Dialog implements android.view.View.OnClickListener
{
    private Activity activity;
    private int position;
    private Spinner slot_end;
    private TextView slot_start;
    private EditText year_branch;
    private Spinner subject_spinner;
    private  TextView day_of_week;
    private ImageView add_slot, cancel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> end_time_adapter;
    HashMap<String, Subject> subjectHashMap;
    private ArrayList<DaySlot> updatedTime;
    String yearBranch;
    WeekDays weekDays;
    Context context;
    ArrayList<String> breakOrNot;



    public AddSlot(int position, Activity activity, HashMap<String, Subject> subjects, String yearBranch, ArrayList<DaySlot> slots , Context context, ArrayList<String> breakOrNot)
    {
        super(activity);
        this.activity = activity;
        this.position = position;
        this.subjectHashMap = subjects;
        this.yearBranch = yearBranch;
        this.updatedTime = slots;
        this.context = context;
        this.breakOrNot = breakOrNot;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.fragment_add_slot);
            slot_end = findViewById(R.id.slot_end_edit_text);
            slot_start = findViewById(R.id.slot_start_edit_text);
            subject_spinner = findViewById(R.id.subject_spinner);
            day_of_week = findViewById(R.id.day_of_week_spinner);
            add_slot = findViewById(R.id.add_slot_button);
            cancel = findViewById(R.id.cancel_slot_button);
            day_of_week.setText("Tuesday"); //TODO:
            weekDays = new WeekDays();

            //yearBranch = year_branch.getText().toString().trim();
            ArrayList<String> slots = new ArrayList<>();
            for (int i = 0; i < updatedTime.size(); i++) {
                slots.add(updatedTime.get(i).getStart_time());
            }

            ArrayList<String> subjectList = new ArrayList<>();
            for (Map.Entry mapElement : subjectHashMap.entrySet()) {
                String sub = ((String)mapElement.getKey());
                subjectList.add(sub);
            }

            adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item,subjectList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject_spinner.setAdapter(adapter);

            int end = slots.size();
            for (int i = position + 1; i < breakOrNot.size(); i++) {
                if (!breakOrNot.get(i).equals("Break"))
                {
                    end = i;
                    break;
                }
            }
            slot_start.setText(slots.get(position));
            ArrayList<String> endslots = position == slots.size() ? new ArrayList<String>() : new ArrayList<String>(slots.subList(position + 1, Math.min(end + 1,slots.size())));
            if(endslots.isEmpty()) endslots.add(Integer.toString(Integer.parseInt(slots.get(position).substring(0,2)) + 1) + ":00 PM");
            //TODO : remove PM from everything
            end_time_adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item,endslots);
            end_time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            slot_end.setAdapter(end_time_adapter);


//            ArrayList<String> days = new ArrayList<>(Arrays.asList(getContext().getResources().getStringArray(R.array.day_of_week)));
//            ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item,days);
//            adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            day_of_week.setAdapter(adapter_2);
            //TODO: get arraylist from database

            add_slot.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }
        catch (Exception e){
            Log.d("excetion",e.toString());
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.add_slot_button:
                AddSlotToDatabase();
                Intent i = new Intent(activity, EditTimeTableActivity.class);
                i.putExtra("Subject", subjectHashMap);
                i.putExtra("yearBranch", yearBranch);
                activity.startActivity(i);
                activity.finish();
                activity.overridePendingTransition(0, 0);
                break;
            case R.id.cancel_slot_button:
                dismiss();
                break;
            default:
                break;
        }

        dismiss();
    }
    private void AddSlotToDatabase()
    {
        try{
            db
                    .collection("timetable")
                    .document("BTech")
                    .collection(yearBranch + "SLOTS")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if(task.isSuccessful())
                            {
                                WeekDays weekDays = new WeekDays();
                                for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                {

                                    weekDays = document.toObject(WeekDays.class);
                                }
//                            Log.d("slots",weekDays.toString());
                                weekDays.addWeekdaySubjectSlot(day_of_week.getText().toString(),slot_start.getText().toString(),slot_end.getSelectedItem().toString(),subject_spinner.getSelectedItem().toString());
//                            Log.d("slots",weekDays.toString());
                                db.collection("timetable").document("BTech").collection(yearBranch+"SLOTS").document("map").set(weekDays);
                            }
                            else
                            {
                                //TODO: exception handling
                            }

                        }
                    });
        }catch (Exception e)
        {
            Log.d("addslot",e.toString());
        }


    }
}
