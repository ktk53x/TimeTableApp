package com.example.timetableapp.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetableapp.R;
import com.example.timetableapp.adapters.SubjectSlotAdapter;
import com.example.timetableapp.model.SubjectSlot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tuesday extends Fragment
{
    private ArrayList<SubjectSlot> tuesday;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public Tuesday(ArrayList<SubjectSlot> weekDay)
    {
        tuesday = weekDay;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_monday, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SubjectSlotAdapter(getActivity(), tuesday);
        recyclerView.setAdapter(adapter);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
}
