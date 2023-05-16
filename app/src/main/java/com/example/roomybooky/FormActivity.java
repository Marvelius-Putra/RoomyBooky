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
    Button f7,f8,f9,f10,f11,f12;
    int floor;
    Integer clicked = 1;
    Context context;

    RecyclerView RoomrecyclerView;
    FormAdapter formAdapter;
    ArrayList<room> roomList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        context = this;

        RoomrecyclerView = findViewById(R.id.roomRecycle);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomsRef = database.getReference("rooms");
        Query query = roomsRef.child("floor1");

        RoomrecyclerView.setHasFixedSize(true);

        //manage the roomRecyclerView

        RoomrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        formAdapter = new FormAdapter(this, roomList);
        RoomrecyclerView.setAdapter(formAdapter);

        //        get variable from previews page
        Intent intent = getIntent();
        int flag = getIntent().getExtras().getInt("flag");

        //      access the layout component
        calendarView = findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.calenderDate);
        f7 = (Button) findViewById(R.id.floor7);
        f8 = (Button) findViewById(R.id.floor8);
        f9 = (Button) findViewById(R.id.floor9);
        f10 = (Button) findViewById(R.id.floor10);
        f11 = (Button) findViewById(R.id.floor11);
        f12 = (Button) findViewById(R.id.floor12);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                myDate.setText(dayOfWeek);
            }
        });

        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggle_group);
        roomList = new ArrayList<>();
        formAdapter = new FormAdapter(this, roomList);
        RoomrecyclerView.setAdapter(formAdapter);

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.floor7:
                            floor = 7;
                            roomList.clear();
                            roomsRef.child("floor1").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        room model = dataSnapshot.getValue(room.class);
                                        roomList.add(model);
                                    }
                                    formAdapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        case R.id.floor8:
                            // Handle button 2 selection
                            floor = 8;
                            roomList.clear();
                            roomsRef.child("floor2").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        room model = dataSnapshot.getValue(room.class);
                                        roomList.add(model);
                                    }
                                    formAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        case R.id.floor9:
                            // Handle button 3 selection
                            floor = 9;
                            break;
                        case R.id.floor10:
                            // Handle button 3 selection
                            floor = 10;
                            break;
                        case R.id.floor11:
                            // Handle button 3 selection
                            floor = 11;
                            break;
                        case R.id.floor12:
                            // Handle button 3 selection
                            floor = 12;
                            break;
                    }
                }
            }

        });
    }
}