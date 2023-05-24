package com.example.roomybooky.UI.User.History;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roomybooky.R;
import com.example.roomybooky.UI.User.UserProfileActivity;

public class BookingDetails extends AppCompatActivity {
//    declare the atributes
    private ImageView profileImg;
    private TextView txtRoomName;
    private TextView txtFloor, txtCategory, txtCapacity, txtStatus;
    private TextView txtName, txtTime, txtDate;
    String roomName, floor, category, capacity, status, name, startTime, endTime, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        // init profile pic image
        profileImg = (ImageView) findViewById(R.id.profPicImg);
        profileImg.setOnClickListener(v -> {
            Intent intent = new Intent(BookingDetails.this, UserProfileActivity.class);
            finish();
            startActivity(intent);
        });

//        init the layout component
        txtRoomName = findViewById(R.id.roomName);
        txtFloor = findViewById(R.id.LB_floor);
        txtCategory = findViewById(R.id.LB_category);
        txtCapacity = findViewById(R.id.LB_maxPerson);
        txtStatus = findViewById(R.id.roomStatus);
        txtName = findViewById(R.id.labelName);
        txtTime = findViewById(R.id.userTime);
        txtDate = findViewById(R.id.userDate);

//        get data from history recycleview
        Intent i = getIntent();
        roomName = i.getStringExtra("roomName");
        floor = i.getStringExtra("floor");
        category = i.getStringExtra("category");
        status = i.getStringExtra("status");
        capacity = i.getStringExtra("capacity");
        name = i.getStringExtra("name");
        startTime = i.getStringExtra("startTime");
        endTime = i.getStringExtra("endTime");
        date = i.getStringExtra("date");

//        change the text layout based on the data
        txtRoomName.setText(roomName);
        txtFloor.setText(floor+"th Floor");
        txtCategory.setText(category);
        txtCapacity.setText(capacity + " Person ");
        txtStatus.setText(status);
        if(status.equals("Pending")) txtStatus.setTextColor(Color.parseColor("#FFA500"));
        if(status.equals("Accepted"))txtStatus.setTextColor(Color.parseColor("#008000"));
        if(status.equals("Declined"))txtStatus.setTextColor(Color.parseColor("#FF0000"));
        txtName.setText(name);
        txtTime.setText(startTime+" - "+endTime);
        txtDate.setText(date);
    }
}