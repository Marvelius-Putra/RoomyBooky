package com.example.roomybooky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.roomybooky.models.room;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

public class FormActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView myDate;
    Button f8,f9,f10;
    int floor;

    Context context;
    String selectedDate, selectedFloor;

    RecyclerView RoomrecyclerView;
    FormAdapter formAdapter;
    ArrayList<room> roomList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        context = this;

        RoomrecyclerView = findViewById(R.id.roomRecycle);

        //        get variable from previews page
        Intent i = getIntent();
        String roomCategory = i.getStringExtra("category");





        RoomrecyclerView.setHasFixedSize(true);

        //manage the roomRecyclerView

        RoomrecyclerView.setLayoutManager(new LinearLayoutManager(this));




        //      access the layout component
        calendarView = findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.calenderDate);

        f8 = (Button) findViewById(R.id.floor8);
        f9 = (Button) findViewById(R.id.floor9);
        f10 = (Button) findViewById(R.id.floor10);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Create a Calendar instance and set the selected date
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                // Format the date using SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                selectedDate = dateFormat.format(calendar.getTime());

                // Set the selected date to your TextView or use it as needed
                myDate.setText(selectedDate);

                // Pass the updated selectedDate to the adapter
                formAdapter.setSelectedDate(selectedDate);
                formAdapter.resetSelection();
            }
        });


        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggle_group);
        roomList = new ArrayList<>();
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.floor8:
                            floor = 8;
                            selectedFloor = "floor8";
                            DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(selectedFloor);
                            Query query = roomsRef.orderByChild("category").equalTo(roomCategory);
                            formAdapter.resetSelection();
                            roomList.clear();
                            formAdapter.setSelectedFloor(selectedFloor);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            room model = dataSnapshot.getValue(room.class);
                                            roomList.add(model);
                                        }
                                        formAdapter.notifyDataSetChanged();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        case R.id.floor9:
                            floor = 9;
                            selectedFloor = "floor9";
                            roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(selectedFloor);
                            query = roomsRef.orderByChild("category").equalTo(roomCategory);
                            formAdapter.resetSelection();
                            roomList.clear();
                            formAdapter.setSelectedFloor(selectedFloor);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            room model = dataSnapshot.getValue(room.class);
                                            roomList.add(model);
                                        }
                                        formAdapter.notifyDataSetChanged();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        case R.id.floor10:
                            floor = 10;
                            selectedFloor = "floor10";
                             roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(selectedFloor);
                             query = roomsRef.orderByChild("category").equalTo(roomCategory);
                            formAdapter.resetSelection();
                            roomList.clear();
                            formAdapter.setSelectedFloor(selectedFloor);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            room model = dataSnapshot.getValue(room.class);
                                            roomList.add(model);
                                        }
                                        formAdapter.notifyDataSetChanged();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                    }
                }
            }
        });

        formAdapter = new FormAdapter(this, roomList, selectedDate, selectedFloor);
        RoomrecyclerView.setAdapter(formAdapter);

    }
}