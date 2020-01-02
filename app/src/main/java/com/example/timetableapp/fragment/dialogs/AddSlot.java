package com.example.timetableapp.fragment.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.timetableapp.R;
import com.example.timetableapp.model.SubjectSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSlot extends Dialog implements android.view.View.OnClickListener
{
    private Activity activity;
    private EditText slot_end, slot_start;
    private Spinner subject_spinner, day_of_week;
    private Button add_slot, cancel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public AddSlot(Activity activity)
    {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_add_slot);
        slot_end = findViewById(R.id.slot_end_edit_text);
        slot_start = findViewById(R.id.slot_start_edit_text);
        subject_spinner = findViewById(R.id.subject_spinner);
        day_of_week = findViewById(R.id.day_of_week_spinner);
        add_slot = findViewById(R.id.add_slot_button);
        cancel = findViewById(R.id.cancel_slot_button);
        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject_spinner.setAdapter(adapter);
        db.collection("timetable").document("BTech").collection("Subject")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            {
                                addSubject(document);
                            }
                        }
                    }
                });

        ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(activity,R.array.day_of_week, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_of_week.setAdapter(adapter_2);
        add_slot.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void addSubject(QueryDocumentSnapshot document) {
        adapter.add(document.get("courseName").toString());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.add_slot_button:
                AddSlotToDatabase();
                dismiss();
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
        String subject_slot_start = slot_start.getText().toString(), subject_slot_end = slot_end.getText().toString();
        String subject_day_of_week = day_of_week.getSelectedItem().toString(), subject = subject_spinner.getSelectedItem().toString();
        SubjectSlot subjectSlot = new SubjectSlot();
        subjectSlot.setSlotStart(subject_slot_start);
        subjectSlot.setSlotEnd(subject_slot_end);
        subjectSlot.setSubject(subject);
        subjectSlot.setDayOfWeek(subject_day_of_week);
        db.collection("timetable").document("BTech").collection("SubjectSlot").add(subjectSlot);
    }
}
