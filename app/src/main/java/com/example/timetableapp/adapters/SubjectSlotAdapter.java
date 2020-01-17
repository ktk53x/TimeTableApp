package com.example.timetableapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableapp.R;
import com.example.timetableapp.fragment.dialogs.ShowSubject;
import com.example.timetableapp.model.Subject;
import com.example.timetableapp.model.SubjectSlot;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectSlotAdapter extends RecyclerView.Adapter<SubjectSlotAdapter.ViewHolder>
{
    private ArrayList<SubjectSlot> slots;
    private Context context;
    private HashMap<String, Subject> subjectHashMap;

    public SubjectSlotAdapter(Context context, ArrayList<SubjectSlot> slots, HashMap<String, Subject> subjectHashMap)
    {
        this.context = context;
        this.slots = slots;
        this.subjectHashMap = subjectHashMap;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView slot_start,subject;
        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            slot_start = itemView.findViewById(R.id.from_time);
            itemView.setOnClickListener(this);
        }

        void bindTo(SubjectSlot slot)
        {
            subject.setText(slot.getSubject());
            slot_start.setText(slot.getSlotStart());
        }

        @Override
        public void onClick(View view) {
            String current_course_name = subject.getText().toString();
            Subject current_subject = subjectHashMap.get(current_course_name);

            if(!current_course_name.equals("Break")) {
                ShowSubject s = new ShowSubject(getAdapterPosition(), context, current_subject);
                s.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                s.show();
            }
        }
    }
}
