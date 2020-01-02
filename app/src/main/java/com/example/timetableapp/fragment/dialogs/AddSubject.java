package com.example.timetableapp.fragment.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.timetableapp.R;
import com.example.timetableapp.model.BTech;
import com.example.timetableapp.model.Subject;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubject extends Dialog implements android.view.View.OnClickListener
{

    private Activity activity;
    private Button add_subject, cancel;
    private EditText subject, venue, professor, year;
    private Spinner branch_spinner;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    HashMap<Integer, String> btech_des = new HashMap<>();
    HashMap<Integer, String> branches = new HashMap<>();

    public AddSubject(Activity activity)
    {
        super(activity);
        this.activity = activity;
    }

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,R.array.branch_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch_spinner.setAdapter(adapter);
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
                dismiss();
                break;
            case R.id.cancel_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
    private void AddSubjectToDatabase()
    {
        String subject_code = subject.getText().toString(), subject_venue = venue.getText().toString();
        String subject_professor = professor.getText().toString(), subject_branch_year = branch_spinner.getSelectedItem().toString() + " " + year.getText().toString();
        Subject subject = new Subject();
        subject.setBranchYear(subject_branch_year);
        subject.setCourseName(subject_code);
        subject.setProfessor(subject_professor);
        subject.setVenue(subject_venue);
        db.collection("timetable").document("BTech").collection("Subject").document(subject_code).set(subject);
    }
}
