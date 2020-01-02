package com.example.timetableapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableapp.R;
import com.example.timetableapp.model.SubjectSlot;

import java.util.ArrayList;

public class SubjectSlotAdapter extends RecyclerView.Adapter<SubjectSlotAdapter.ViewHolder>
{
    private ArrayList<SubjectSlot> slots;
    private Context context;

    public SubjectSlotAdapter(Context context, ArrayList<SubjectSlot> slots)
    {
        this.context = context;
        this.slots = slots;
    }
    @NonNull
    @Override
    public SubjectSlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectSlotAdapter.ViewHolder holder, int position) {
        SubjectSlot slot = slots.get(position);
        holder.bindTo(slot);
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView slot_start, slot_end, subject;
        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            slot_end = itemView.findViewById(R.id.to_time);
            slot_start = itemView.findViewById(R.id.from_time);
        }

        void bindTo(SubjectSlot slot)
        {
            subject.setText(slot.getSubject());
            slot_end.setText(slot.getSlotEnd());
            slot_start.setText(slot.getSlotStart());
        }
    }
}
