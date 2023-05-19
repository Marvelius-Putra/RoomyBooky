package com.example.roomybooky;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.models.room;

import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormHolder> {
    public  Context context;
    public  ArrayList<room> roomList;
    public int selectedItem = -1; // Track the selected item position
    public  String selectedDate;
    public  String selectedFloor;

    public FormAdapter(Context context, ArrayList<room> roomList, String selectedDate, String selectedFloor) {
        this.context = context;
        this.roomList = roomList;
        this.selectedDate = selectedDate;
        this.selectedFloor = selectedFloor;
    }

    @NonNull
    @Override
    public FormHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_component, parent, false);
        final FormHolder holder = new FormHolder(v);

        // Set click listener for the imageView
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    selectedItem = position; // Update the selected item position
                    notifyDataSetChanged();
                }
            }
        });
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = holder.time.getCheckedRadioButtonId();
                String selectedTime = "";
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = holder.itemView.findViewById(selectedRadioButtonId);
                    selectedTime = selectedRadioButton.getText().toString();
                }
                room selectedRoom = roomList.get(holder.getAdapterPosition());

                String category = selectedRoom.getCategory();

                String name = selectedRoom.getRoom();

                Intent i = new Intent(context, BookConfirm.class);
                i.putExtra("selectedTime", selectedTime);
                i.putExtra("selectedFloor", selectedFloor);
                i.putExtra("name", name);
                i.putExtra("selectedDate", selectedDate);
                context.startActivity(i);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FormHolder holder, int position) {
        room model = roomList.get(position);
        String roomName = model.getRoom();
        holder.room_name.setText(roomName);

        // Set the radio button names
        holder.s1.setText(model.getAvailability().get("sh1"));
        holder.s2.setText(model.getAvailability().get("sh2"));
        holder.s3.setText(model.getAvailability().get("sh3"));
        holder.time.clearCheck();

        // Set the radio button status and enable/disable them based on status
        // Set the radio button status and enable/disable them based on the selected state

        boolean isSelected = position == selectedItem;
        boolean isSt1Enabled = model.getStatus().get("st1");
        boolean isSt2Enabled = model.getStatus().get("st2");
        boolean isSt3Enabled = model.getStatus().get("st3");
        holder.s1.setEnabled(isSelected && isSt1Enabled);
        holder.s2.setEnabled(isSelected && isSt2Enabled);
        holder.s3.setEnabled(isSelected && isSt3Enabled);

        // Set the color of the imageView based on the selected state
        int color;
        if (isSelected) {
            color = R.color.green;
        } else {
            color = R.color.teal_200;
        }
        // Set the button enabled/disabled state based on the selected state
        holder.book.setEnabled(isSelected);
        holder.imageView.setColorFilter(ContextCompat.getColor(context, color));
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
    public void resetSelection() {
        selectedItem = -1;
        notifyDataSetChanged();
    }

    public void setSelectedFloor(String selectedFloor) {
        this.selectedFloor = selectedFloor;
    }

    public  class FormHolder extends RecyclerView.ViewHolder {
        TextView room_name;
        RadioGroup time;
        RadioButton s1;
        RadioButton s2;
        RadioButton s3;
        ImageView imageView;
        Button book;


        public FormHolder(@NonNull View itemView) {
            super(itemView);
            room_name = itemView.findViewById(R.id.roomName);
            time = itemView.findViewById(R.id.radioTime);
            s1 = itemView.findViewById(R.id.sh1);
            s2 = itemView.findViewById(R.id.sh2);
            s3 = itemView.findViewById(R.id.sh3);
            imageView = itemView.findViewById(R.id.roomList);
            book = itemView.findViewById(R.id.btn_book);

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedRadioButtonId= time.getCheckedRadioButtonId();
                    String selectedTime = "";
                    if(selectedRadioButtonId != - 1){
                        RadioButton selectedRadioButton = itemView.findViewById(selectedRadioButtonId);
                        selectedTime = selectedRadioButton.getText().toString();
                    }
                    room selectedRoom = roomList.get(getAdapterPosition());
                    int capacity = selectedRoom.getCapacity();
                    String category = selectedRoom.getCategory();

                    String name = selectedRoom.getRoom();

                    Intent i = new Intent(context, BookConfirm.class);
                    i.putExtra("selectedTime", selectedTime);
                    i.putExtra("capacity", capacity);
                    i.putExtra("selectedFloor", selectedFloor);
                    i.putExtra("name", name);
                    i.putExtra("selectedDate", selectedDate);
                    context.startActivity(i);
                }
            });
        }
    }
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
}

