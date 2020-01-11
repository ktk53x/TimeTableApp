package com.example.timetableapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableapp.R;
import com.example.timetableapp.adapters.SubjectSlotAdapter;
import com.example.timetableapp.model.SubjectSlot;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class Weekday extends Fragment {
    private ArrayList<SubjectSlot> thursday;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    TextView to_time;

    public Weekday(ArrayList<SubjectSlot> weekDay)
    {
        thursday = weekDay;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_monday, container, false);
        Collections.sort(thursday, new SubjectSlot.SortByTime());
        to_time = rootView.findViewById(R.id.to_time);
        String endTime = thursday.size()==0 ? "" : thursday.get(thursday.size()-1).getSlotEnd();
        to_time.setText(endTime);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SubjectSlotAdapter(getActivity(), thursday);
        recyclerView.setAdapter(adapter);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

}
