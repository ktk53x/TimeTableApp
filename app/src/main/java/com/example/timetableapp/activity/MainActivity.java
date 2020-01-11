package com.example.timetableapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timetableapp.EditTimeTableActivity;
import com.example.timetableapp.fragment.dialogs.AddSlot;
import com.example.timetableapp.fragment.dialogs.AddSubject;
import com.example.timetableapp.R;
import com.example.timetableapp.utility.TimeTableUtilities;
import com.example.timetableapp.model.BTech;
import com.example.timetableapp.model.Subject;
import com.example.timetableapp.model.SubjectSlot;
import com.example.timetableapp.model.WeekDays;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    private EditText roll_number;
    private TextView hello_world;
    public final String TAG = "Kartikeya";
    HashMap<String, Integer> details;
    HashMap<String, Subject> subjects;
    BTech bTech = new BTech();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String branchYear;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll_number = findViewById(R.id.roll_number_edit_text);
        hello_world = findViewById(R.id.textView2);
    }

    public void AddSubjectDialogBox(View view)
    {
        AddSubject s = new AddSubject(MainActivity.this);
        s.show();
    }

    public void AddSlotDialogBox(View view)
    {
        AddSlot s = new AddSlot(0,MainActivity.this);
        s.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        s.show();
    }

    public void ViewTimeTable(View view)
    {
//        bTech.getSubject().clear();
//        bTech.getSubjectSlot().clear();
        HashMap<Integer, String> btech_des = new HashMap<>();
        HashMap<Integer, String> branches = new HashMap<>();
        btech_des.put(1, "BTech");
        btech_des.put(2, "BDes");
        branches.put(1, "CSE");
        branches.put(2, "ECE");
        branches.put(3, "ME");
        branches.put(4, "CE");
        branches.put(6, "BT");
        branches.put(7, "CL");
        branches.put(8, "EEE");
        branches.put(21, "EP");
        branches.put(22, "CST");
        branches.put(23, "MC");
        details = TimeTableUtilities.roll_number_parser(roll_number.getText().toString());
        String year = "None", course_type = "None", branch = "None";
        if(details.get("year") != null)
            year = Objects.requireNonNull(details.get("year")).toString();
        if(details.get("course_type") != null)
            course_type = btech_des.get(details.get("course_type"));
        if(details.get("branch") != null)
            branch = branches.get(details.get("branch"));

        branchYear = year + " " + branch;
        getSubjects(branchYear);


    }
    public void getSubjects(final String yearBranch)
    {
        db
                .collection("timetable")
                .document("BTech")
                .collection(yearBranch)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            {
                                Subject temp = document.toObject(Subject.class);
                                subjects.put(temp.getCourseName(),temp);
                            }
                            getSlots(yearBranch);
                        }
                        else
                        {
                            //TODO: exception handling
                        }
                    }
                });
    }

    public void getSlots(String yearBranch)
    {
        db
                .collection("timetable")
                .document("BTech")
                .collection(branchYear + "Slots")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            WeekDays temp = new WeekDays();
                            for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult()))
                            {
                                temp = document.toObject(WeekDays.class);
                            }
                            startActivity(ShowTimeTable.getInstance(MainActivity.this, temp, subjects));
                        }
                    }
                });
    }

//
//    private void showTimeTable(WeekDays weekDays) {
//        for(SubjectSlot subjectSlot : bTech.getSubjectSlot()){
//            for(Subject subject :bTech.getSubject()){
//                if(subject.getCourseName().equals(subjectSlot.getSubject())){
//                    {
//                        weekDays.addWeekdaySubjectSlot(subjectSlot.getDayOfWeek(), subjectSlot);
//                    }
//                }
//            }
//        }
//    }

    public void EditTimeTable(View view)
    {
        Intent intent = new Intent(getBaseContext(), EditTimeTableActivity.class);
        startActivity(intent);

    }
}
