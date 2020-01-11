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
import com.example.timetableapp.model.SubjectSlot;

import java.util.ArrayList;

public class DaySlotAdapter extends RecyclerView.Adapter<DaySlotAdapter.ViewHolder>
{
    private ArrayList<DaySlot> slots;
    private Context context;
    private Activity activity;
    private send_data portal;

    public DaySlotAdapter(Context context, ArrayList<DaySlot> slots, Activity activity)
    {
        this.context = context;
        this.slots = slots;
        this.activity = activity;
//        this.portal = portal;
    }
    void AddSlotDialog(View view, int position)
    {
        AddSlot s = new AddSlot(position,activity);
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
        ImageView status = slots.get(position).getStatus();
        holder.bindTo(slot, status, portal);

    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView time_slot;
        private ImageView add_button;
        DaySlotAdapter adapter;
        ViewHolder(@NonNull View itemView, DaySlotAdapter adapter)
        {
            super(itemView);
            time_slot = itemView.findViewById(R.id.slot_time);
            add_button = itemView.findViewById(R.id.add_slot_button);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        void bindTo(String slot, ImageView status, final send_data portal)
        {
            time_slot.setText(slot);
            add_button.setImageResource(R.drawable.ic_add_circle_black_24dp);
        }


        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            AddSlotDialog(view,position);
            portal.send(position);
        }
    }
}