package com.example.timetableapp.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timetableapp.R;
import com.example.timetableapp.model.SubjectSlot;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monday extends Fragment {

    private ArrayList<SubjectSlot> weekDay;

    public Monday(ArrayList<SubjectSlot> monday) {
        weekDay = monday;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Kartikeya", weekDay.toString());
        return inflater.inflate(R.layout.fragment_monday, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.subject)).setText(weekDay.get(0).getSubject());
    }
}
