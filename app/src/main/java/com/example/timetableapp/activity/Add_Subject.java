package com.example.timetableapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.timetableapp.R;
import com.example.timetableapp.model.Subject;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;



public class Add_Subject extends AppCompatActivity implements android.view.View.OnClickListener{

    private ImageView add_subject, cancel;
    private EditText subject, venue, professor, year;
    private Spinner branch_spinner;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    HashMap<Integer, String> btech_des = new HashMap<>();
    HashMap<Integer, String> branches = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_add_subject);
        add_subject = findViewById(R.id.add_subject_button);
        cancel = findViewById(R.id.cancel_button);
        subject = findViewById(R.id.add_subject_edit_text);
        venue = findViewById(R.id.venue_edit_text);
        professor = findViewById(R.id.professor_edit_text);
        year = findViewById(R.id.year_edit_text);
        branch_spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.branch_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch_spinner.setAdapter(adapter);
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
        add_subject.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.add_subject_button:
                AddSubjectToDatabase();
//                dismiss();
                break;
            case R.id.cancel_button:
//                dismiss();
                break;
            default:
                break;
        }
//        dismiss();
    }
    private void AddSubjectToDatabase()
    {
        String subject_code = subject.getText().toString(), subject_venue = venue.getText().toString();
        String subject_professor = professor.getText().toString(), year_branch = year.getText().toString() + branch_spinner.getSelectedItem().toString();
        Subject subject = new Subject(subject_code,subject_professor,subject_venue);
        db.collection("timetable").document("BTech").collection(year_branch).document(subject_code).set(subject);
    }
}

