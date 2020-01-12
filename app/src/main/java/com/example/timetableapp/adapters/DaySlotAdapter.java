package com.example.timetableapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableapp.Interfaces.send_data;
import com.example.timetableapp.R;
import com.example.timetableapp.activity.MainActivity;
import com.example.timetableapp.fragment.dialogs.AddSlot;
import com.example.timetableapp.fragment.dialogs.AddSubject;
import com.example.timetableapp.model.DaySlot;
import com.example.timetableapp.model.Subject;
import com.example.timetableapp.model.SubjectSlot;

import java.util.ArrayList;
import java.util.HashMap;

public class DaySlotAdapter extends RecyclerView.Adapter<DaySlotAdapter.ViewHolder>
{
    private ArrayList<DaySlot> slots;
    private Context context;
    private Activity activity;
    private HashMap<String, Subject> subjects;
    private String yearBranch;
//    private send_data portal;

    public DaySlotAdapter(Context context, ArrayList<DaySlot> slots, Activity activity, HashMap<String, Subject> subjects, String yearBranch)
    {
        this.context = context;
        this.slots = slots;
        this.activity = activity;
        this.subjects = subjects;
//        this.portal = portal;
        this.yearBranch = yearBranch;
    }
    void AddSlotDialog(View view, int position, String yearBranch)
    {
        AddSlot s = new AddSlot(position,activity, subjects, yearBranch, slots);
        s.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        s.show();
    }
    @NonNull
    @Override
    public DaySlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item2, parent, false),this);
    }

    @Override
    public void onBindViewHolder(@NonNull final DaySlotAdapter.ViewHolder holder, int position) {
        String slot = slots.get(position).getStart_time();
        TextView status = slots.get(position).getStatus();
        holder.bindTo(slot, status);

    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView time_slot;
        private TextView add_button;
        DaySlotAdapter adapter;
        ViewHolder(@NonNull View itemView, DaySlotAdapter adapter)
        {
            super(itemView);
            time_slot = itemView.findViewById(R.id.slot_time);
            add_button = itemView.findViewById(R.id.add_slot_button);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        void bindTo(String slot, TextView status)
        {
            time_slot.setText(slot);
//            add_button.setImageResource(R.drawable.ic_add_circle_black_24dp);
        }


        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            AddSlotDialog(view,position, yearBranch);
//            portal.send(position);
        }
    }
}
