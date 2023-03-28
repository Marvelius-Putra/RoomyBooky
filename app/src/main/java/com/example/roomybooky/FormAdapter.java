package com.example.roomybooky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.models.room;

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
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public  static class FormHolder extends  RecyclerView.ViewHolder{
        TextView room_name;
        public FormHolder(@NonNull View itemView) {
            super(itemView);
            room_name = itemView.findViewById(R.id.roomName);
        }
    }
}
