package com.example.roomybooky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.models.room;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormHolder> {
    Context context;
    ArrayList<room> roomList;

    public FormAdapter(Context context, ArrayList<room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public FormHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_component, parent, false );
        return new FormHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FormHolder holder, int position) {
        room model = roomList.get(position);
        String roomName = model.getName();
        holder.room_name.setText(roomName);

        if (model.getAvailability() != null) {
            String sh1 = model.getAvailability().get("sh1");
            String sh2 = model.getAvailability().get("sh2");
            // Set the availability times in the respective TextViews
            holder.s1.setText(sh1);
            holder.s2.setText(sh2);

            // Set the radio button status and enable/disable them based on status

            holder.s1.setEnabled((model.getStatus().get("st1")));

            holder.s2.setEnabled((model.getStatus().get("st2")));
        }

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public  static class FormHolder extends  RecyclerView.ViewHolder{
        TextView room_name;
        RadioGroup time;
        RadioButton s1;
        RadioButton s2;

        public FormHolder(@NonNull View itemView) {
            super(itemView);
            room_name = itemView.findViewById(R.id.roomName);
            time = itemView.findViewById(R.id.radioTime);
            s1 = itemView.findViewById(R.id.sh1);
            s2 = itemView.findViewById(R.id.sh2);

        }
    }
}
