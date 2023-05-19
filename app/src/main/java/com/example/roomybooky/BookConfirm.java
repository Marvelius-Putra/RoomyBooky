package com.example.roomybooky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomybooky.models.booking;
import com.example.roomybooky.models.room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookConfirm extends AppCompatActivity {
    TextView book_date, book_floor, book_room, book_time, book_category;
    EditText book_reason, book_people;
    DatabaseReference bookingsRef;
    String user_nim, user_name, user_email, category, floorValue;
    int members, floorRoom = 0;
    Button btn_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_confirm);

        book_date = findViewById(R.id.txtDate);
        book_floor = findViewById(R.id.txtFloor);
        book_room = findViewById(R.id.txtRoom);
        book_time = findViewById(R.id.txtTime);
        book_category = findViewById(R.id.txtCategory);
        book_reason = (EditText) findViewById(R.id.edtReason);
        book_people = (EditText) findViewById(R.id.edtPeople);
        btn_req = (Button) findViewById(R.id.btnRequest);

        Intent i = getIntent();
        String selectedTime = i.getStringExtra("selectedTime");
        String roomName = i.getStringExtra("name");
        String selectedDate = i.getStringExtra("selectedDate");
        String selectedFloor = i.getStringExtra("selectedFloor");

        // Parse the selected time to a Date object using SimpleDateFormat
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date time;
        try {
            time = timeFormat.parse(selectedTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Create a Calendar instance and set the selected time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        // Add 2 hours to the selected time
        calendar.add(Calendar.HOUR_OF_DAY, 2);

        // Format the new time
        String updatedTime = timeFormat.format(calendar.getTime());

        book_date.setText(selectedDate);
        book_room.setText(roomName);
        book_time.setText(selectedTime + " - " + updatedTime);



        if (selectedFloor != null) {
            DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(selectedFloor);
            Query query = roomsRef.orderByChild("room").equalTo(roomName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                            room room = roomSnapshot.getValue(room.class);
                            if (room != null) {
                                floorRoom = room.getFloor();
                                category = room.getCategory();
                                floorValue = String.valueOf(floorRoom);
                                book_floor.setText("Floor: " + floorValue);
                                book_category.setText(category);
                                break; // Assuming there is only one room with the selected name
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error
                }
            });
            // Rest of your code
        } else {
            book_floor.setText("Floor: " + selectedFloor);
        }

        //get the current logged-in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            String userUID = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

            //Retrieve the user data based on the user UID
            userRef.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        user_nim = snapshot.child("nim").getValue(String.class);
                        user_name = snapshot.child("name").getValue(String.class);
                        user_email = snapshot.child("email").getValue(String.class);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //Initialize database for bookings
        bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
        btn_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_description = book_reason.getText().toString().trim();
                String input_people = book_people.getText().toString().trim();
                if (!input_people.isEmpty()) {
                    try {
                        // Convert the input text to an integer
                        members = Integer.parseInt(input_people);

                        // Use the integer value as needed
                        // For example, display it or perform calculations

                        addBookingToDatabase(user_name, user_email, user_nim, roomName, floorRoom, category, selectedDate, members, selectedTime, updatedTime, input_description, "Pending");
                    } catch (NumberFormatException e) {
                        // Handle the case where the input cannot be parsed as an integer
                        Toast.makeText(BookConfirm.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where the input is empty
                    Toast.makeText(BookConfirm.this, "Input is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void addBookingToDatabase(String username, String email, String nim, String room, int floor, String category, String date, int member, String startTime, String endTime, String description, String status){
        String bookingKey = "B" + bookingsRef.push().getKey();
        booking model = new booking(username, email, nim, room, floor, category, date, member, startTime, endTime, description, status, bookingKey);

        bookingsRef.child(bookingKey).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(BookConfirm.this, "Your request has been sent successfully", Toast.LENGTH_SHORT).show();
                    displayFrag();
                }
                else{
                    Toast.makeText(BookConfirm.this, "request Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void displayFrag(){
        ConfirmFragment dialogFragment = new ConfirmFragment();
        dialogFragment.show(getSupportFragmentManager(),"MyFragment");
    }
}

