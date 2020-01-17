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
public class ShowSubject extends Dialog implements android.view.View.OnClickListener
{

    private int position;
    private Subject subject;
    private Context context;

    private TextView course_code, course_name, professor, venue;
    private ImageView cancel;

    public ShowSubject(int position, Context context, Subject subject)
    {
        super(context);
        this.position = position;
        this.subject = subject;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.fragment_show_subject);
            course_name = findViewById(R.id.course_name);
            course_code = findViewById(R.id.course_code);
            professor = findViewById(R.id.professor);
            venue = findViewById(R.id.location);
            cancel = findViewById(R.id.cancel_slot_button);
            cancel.setOnClickListener(this);
            course_name.setText(subject.getCourseName());
            course_code.setText(subject.getCourseCode());
            professor.setText(subject.getProfessor());
            venue.setText(subject.getVenue());
        }
        catch (Exception e){
            Log.d("excetion",e.toString());
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.cancel_slot_button)
        {
                dismiss();
        }
    }

}
