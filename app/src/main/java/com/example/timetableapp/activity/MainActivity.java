package com.example.timetableapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        AddSlot s = new AddSlot(MainActivity.this);
        s.show();
    }

    public void ViewTimeTable(View view)
    {
        bTech.getSubject().clear();
        bTech.getSubjectSlot().clear();
        HashMap<Integer, String> btech_des = new HashMap<>();
        HashMap<Integer, String> branches = new HashMap<>();
        btech_des.put(1, "BTech");
        btech_des.put(2, "BDes");
        branches.put(1, "Computer Science And Engineering");
        branches.put(2, "Electronics And Communication Engineering");
        branches.put(3, "Mechanical Engineering");
        branches.put(4, "Civil Engineering");
        branches.put(6, "Bio Technology");
        branches.put(7, "Chemical Engineering");
        branches.put(8, "Electrical Engineering");
        branches.put(21, "Engineering Physics");
        branches.put(22, "Chemical Science And Technology");
        branches.put(23, "Mathematics And Computing");
        details = TimeTableUtilities.roll_number_parser(roll_number.getText().toString());
        String year = "None", course_type = "None", branch = "None";
        if(details.get("year") != null)
            year = Objects.requireNonNull(details.get("year")).toString();
        if(details.get("course_type") != null)
            course_type = btech_des.get(details.get("course_type"));
        if(details.get("branch") != null)
            branch = branches.get(details.get("branch"));

        branchYear = branch + " " + year;

        getSubjects();


    }
    public void getSubjects()
    {
        db
                .collection("timetable")
                .document("BTech")
                .collection("Subject")
                .whereEqualTo("branchYear", branchYear)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            {
                                bTech.addSubject(Objects.requireNonNull(document.toObject(Subject.class)));
                            }
                            getSlots();
                        }
                    }
                });
    }

    public void getSlots()
    {
        db
                .collection("timetable")
                .document("BTech")
                .collection("SubjectSlot")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult()))
                            {
                                bTech.addSubjectSlot(Objects.requireNonNull(document.toObject(SubjectSlot.class)));
                            }
                            showTimeTable();
                        }
                    }
                });
    }

    private void showTimeTable() {
        WeekDays weekDays = new WeekDays();
        for(SubjectSlot subjectSlot : bTech.getSubjectSlot()){
            for(Subject subject :bTech.getSubject()){
                if(subject.getCourseName().equals(subjectSlot.getSubject())){
                    {
                        weekDays.addWeekdaySubjectSlot(subjectSlot.getDayOfWeek(), subjectSlot);
                    }
                }
            }
        }
        startActivity(ShowTimeTable.getInstance(this, weekDays));
    }
}
