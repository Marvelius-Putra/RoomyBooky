package com.example.roomybooky.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomybooky.data.user.Booking;
import com.example.roomybooky.data.Room;
import com.example.roomybooky.R;
import com.example.roomybooky.ui.ProfileActivity;
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

public class BookingConfirmActivity extends AppCompatActivity {
  private TextView btnBack;
  private ImageView profPicImg;
  private TextView dateTxt, floorTxt, roomTxt, timeTxt, categoryTxt;
  private EditText bookingReason, numberPeople;
  private AppCompatButton btnSendRequest;
  DatabaseReference bookingsRef;
  String user_nim, user_name, user_email, category, floorValue;
  int floorRoom = 0;
  Long members;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking_confirm);

    // init button back;
    btnBack = (TextView) findViewById(R.id.btnBackToForm);
    btnBack.setOnClickListener(v -> {
      setIntent(new Intent(BookingConfirmActivity.this, BookingFormActivity.class));
      finish();
      startActivity(getIntent());
    });

    // init profile picture
    profPicImg = (ImageView) findViewById(R.id.profPicImg);
    profPicImg.setOnClickListener(v -> {
      setIntent(new Intent(BookingConfirmActivity.this, ProfileActivity.class));
      finish();
      startActivity(getIntent());
    });

    // init component booking confirm
    dateTxt = (TextView) findViewById(R.id.dateOfBookingDetail);
    floorTxt = (TextView) findViewById(R.id.floorBookingDetail);
    roomTxt = (TextView) findViewById(R.id.roomBookingDetail);
    categoryTxt = (TextView) findViewById(R.id.categoryBookingDetail);
    timeTxt = (TextView) findViewById(R.id.timeBookingDetail);

    btnSendRequest = (AppCompatButton) findViewById(R.id.btnSendRequest);
    bookingReason = (EditText) findViewById(R.id.inputReasonField);
    numberPeople = (EditText) findViewById(R.id.inputNumberPeopleField);

//    get data from formActivity
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

    dateTxt.setText(selectedDate);
    roomTxt.setText(roomName);
    timeTxt.setText(selectedTime + " - " + updatedTime);

    // read the room database information based on the user selection
    if (selectedFloor != null) {
      DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(selectedFloor);
      Query query = roomsRef.orderByChild("room").equalTo(roomName);
      query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          if (snapshot.exists()) {
            for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
              Room room = roomSnapshot.getValue(Room.class);
              if (room != null) {
                floorRoom = room.getFloor();
                category = room.getCategory();
                floorValue = String.valueOf(floorRoom);
                floorTxt.setText(floorValue);
                categoryTxt.setText(category);
                break; // Assuming there is only one room with the selected name
              }
            }
          }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {}
      });
    } else {
      floorTxt.setText(selectedFloor);
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
        public void onCancelled(@NonNull DatabaseError error) { }
      });
    }

    //Initialize database for bookings
    bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
    btnSendRequest.setOnClickListener(v -> {
      if (!validationReason()) return;
      String input_description = bookingReason.getText().toString().trim();
      String input_people = numberPeople.getText().toString().trim();
      try {
        members = Long.parseLong(input_people);
        if (members <= 0 || members > 50) {
          Toast.makeText(this, "Number of people isn't eligible", Toast.LENGTH_SHORT).show();
          return;
        }
        addBookingToDatabase(user_name, user_email, user_nim, roomName, floorRoom, category, selectedDate, members, selectedTime, updatedTime, input_description, "Pending");
      } catch (NumberFormatException e) {
        Toast.makeText(this, "Number of people isn't eligible", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private  void addBookingToDatabase(String username, String email, String nim, String room, int floor, String category, String date, Long member, String startTime, String endTime, String description, String status){
    String bookingKey = "B" + bookingsRef.push().getKey();
    Booking model = new Booking(username, email, nim, room, floor, category, date, member, startTime, endTime, description, status, bookingKey);

    bookingsRef.child(bookingKey).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()) showAlertDialog();
        else {
          Toast.makeText(BookingConfirmActivity.this, "request Failed", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  public boolean validationReason() {
    if (bookingReason.getText().toString().isEmpty() || numberPeople.getText().toString().isEmpty()) {
      Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
      return false;
    }
    else if (bookingReason.getText().toString().length() <= 5) {
      Toast.makeText(this, "Reason is too short", Toast.LENGTH_SHORT).show();
      return false;
    }
    else if (bookingReason.getText().toString().length() > 30) {
      Toast.makeText(this, "Reason is too long", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  private void showAlertDialog() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ConfirmFragment confirmFragment = new ConfirmFragment();
    confirmFragment.show(fragmentManager, "alert_dialog");
  }
}