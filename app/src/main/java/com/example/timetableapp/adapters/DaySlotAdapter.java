package com.example.timetableapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableapp.EditTimeTableActivity;
import com.example.timetableapp.Interfaces.send_data;
import com.example.timetableapp.R;
import com.example.timetableapp.activity.MainActivity;
import com.example.timetableapp.fragment.dialogs.AddSlot;
import com.example.timetableapp.fragment.dialogs.AddSubject;
import com.example.timetableapp.fragment.dialogs.DelSlot;
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
    private int j;
    ArrayList<String> breakOrNot;
//    private send_data portal;

    public DaySlotAdapter(Context context, ArrayList<DaySlot> slots, Activity activity, HashMap<String, Subject> subjects, String yearBranch, ArrayList<String> breakOrNot, int j)
    {
        this.context = context;
        this.slots = slots;
        this.activity = activity;
        this.subjects = subjects;
//        this.portal = portal;
        this.yearBranch = yearBranch;
        this.breakOrNot = breakOrNot;
        this.j = j;
    }
    void AddSlotDialog(View view, int position, String yearBranch)
    {
        AddSlot s = new AddSlot(position,activity, subjects, yearBranch, slots, context, breakOrNot, j);
        s.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        s.show();
    }
    void DelSlotDialog(View view, int position, String yearBranch)
    {
        DelSlot s = new DelSlot(position,activity, subjects, yearBranch, slots, context, breakOrNot, j);
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
        String text = status.getText().toString();
        Drawable image = status.getBackground();
        holder.bindTo(slot, text, image);

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
            add_button = itemView.findViewById(R.id.add_button);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        void bindTo(String slot, String text, Drawable image)
        {
            time_slot.setText(slot);
            add_button.setText(text);
            add_button.setBackground(image);

            String curBreak = breakOrNot.get(getAdapterPosition());
            if(curBreak.equals("Break"))
            {
                add_button.setMinHeight(120);
                add_button.setMinWidth(100);

            }else{
                add_button.setPadding(70, 15, 70, 15);

            }
        }


        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            String curBreak = breakOrNot.get(position);
            if(curBreak.equals("Break"))
            {
                AddSlotDialog(view,position, yearBranch);
            }
            else
            {
                DelSlotDialog(view, position, yearBranch);
            }
        }
    }
}
