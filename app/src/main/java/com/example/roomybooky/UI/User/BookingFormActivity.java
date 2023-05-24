package com.example.roomybooky.UI.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomybooky.Data.User.Booking;
import com.example.roomybooky.Data.room;
import com.example.roomybooky.R;
import com.example.roomybooky.UI.HomePage;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BookingFormActivity extends AppCompatActivity {
  private TextView btnBack, myDate;
  private ImageView profPicImg;
  private AppCompatButton btnConfirm;
  private Intent intent;

  private CalendarView calendarView;
  private Button f8,f9,f10;
  private int floor;


  private Context context;
  private String roomCategory;
  private String selectedDate, selectedFloor, acceptedRoomName, acceptedStartTime, acceptedBookDate;


  private RecyclerView RoomrecyclerView;
  private FormAdapter formAdapter;
  private ArrayList<room> roomList;
  private int lastCheckedButtonId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking_form);

    // init button back;
    btnBack = (TextView) findViewById(R.id.btnBackToForm);
    btnBack.setOnClickListener(v -> {
      setIntent(new Intent(BookingFormActivity.this, HomePage.class));
      finish();
      startActivity(getIntent());
    });

    // init profile picture
    profPicImg = (ImageView) findViewById(R.id.profPicImg);
    profPicImg.setOnClickListener(v -> {
      setIntent(new Intent(BookingFormActivity.this, UserProfileActivity.class));
      finish();
      startActivity(getIntent());
    });

    // init myDate


//    // init confirm button
//    btnConfirm = (AppCompatButton) findViewById(R.id.btnConfirm);
//    btnConfirm.setOnClickListener(v -> {
//      // kasih kondisi kalo misalnya input kosong
//      // if (!isInputEmpty()) {
//      //    Toast;
//      //    return;
//      // }
//      setIntent(new Intent(BookingFormActivity.this, BookingConfirmActivity.class));
//      finish();
//      startActivity(getIntent());
//    });

    /* Di bawah kode marvel */

    // get variable from previews page
    Intent i = getIntent();
    roomCategory = i.getStringExtra("category");

    //manage the roomRecyclerView
    RoomrecyclerView = findViewById(R.id.roomRecycle);
    RoomrecyclerView.setHasFixedSize(true);
    RoomrecyclerView.setLayoutManager(new LinearLayoutManager(this));

    //      access the layout component
    calendarView = findViewById(R.id.calendarView);
    myDate = (TextView) findViewById(R.id.calenderDate);

    f8 = (Button) findViewById(R.id.floor8);
    f9 = (Button) findViewById(R.id.floor9);
    f10 = (Button) findViewById(R.id.floor10);

//    if the category SADC or LKC disable the button
    if ("SADC".equals(roomCategory) || "LKC".equals(roomCategory)) {
      f8.setVisibility(View.GONE);
      f9.setVisibility(View.GONE);
      f10.setVisibility(View.GONE);
    }

    // Set the initial date to today's date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    calendarView.setDate(calendar.getTimeInMillis(), true, true);

    MaterialButtonToggleGroup toggleGroup = findViewById(R.id.floorList);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    selectedDate = dateFormat.format(calendar.getTime());
    myDate.setText(selectedDate);
    roomList = new ArrayList<>();

    selectedFloor = "floor8";



//    when user click the date
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

        if(toggleGroup.getCheckedButtonId() == -1){
          toggleGroup.check(R.id.floor8);
//          lastCheckedButtonId = R.id.floor8;
        }



        if (roomCategory.equals("SADC") || roomCategory.equals("LKC")) {
          floor = 4;
          selectedFloor = "floor4";
          displayRoomList();
        }
        else{
          displayRoomList();
        }
//        if(selectedFloor!= null && !(roomCategory.equals("SADC") && !(roomCategory.equals("LKC")))){
//          Toast.makeText(BookingFormActivity.this, "hi", Toast.LENGTH_SHORT).show();
//          displayRoomList();
//        }


      }
    });

//  choose floor


//    display roomList
    toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
      @Override
      public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        f8.setOnClickListener(v -> {
            toggleGroup.check(R.id.floor8);
            lastCheckedButtonId = R.id.floor8;
            // Perform necessary actions for floor 8
            floor = 8;
            selectedFloor = "floor8";
            displayRoomList();
        });

        f9.setOnClickListener(v -> {
          toggleGroup.check(R.id.floor9);
          lastCheckedButtonId = R.id.floor9;
          // Perform necessary actions for floor 8
          floor = 9;
          selectedFloor = "floor9";
          displayRoomList();
        });

        f10.setOnClickListener(v -> {
          toggleGroup.check(R.id.floor10);
          lastCheckedButtonId = R.id.floor10;
          // Perform necessary actions for floor 8
          floor = 10;
          selectedFloor = "floor10";
          displayRoomList();
        });
      }
    });
    formAdapter = new FormAdapter(this, roomList, selectedDate, selectedFloor,acceptedBookDate, acceptedStartTime, acceptedRoomName);
    RoomrecyclerView.setAdapter(formAdapter);
  }

  private void displayRoomList(){
    DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(selectedFloor);
    Query query = roomsRef.orderByChild("category").equalTo(roomCategory);
    formAdapter.resetSelection();
    roomList.clear();
    formAdapter.setSelectedFloor(selectedFloor);

    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
          checkRoomAvailability(selectedDate);
          for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            room model = dataSnapshot.getValue(room.class);
            if (model.getCategory().equals(roomCategory)) {  // Add this condition
              roomList.add(model);
            }
          }
          formAdapter.notifyDataSetChanged();
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }

  private void checkRoomAvailability(String selectedDate) {
    DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
    Query query = bookingsRef.orderByChild("date").equalTo(selectedDate);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
          for(DataSnapshot dataSnapshot:snapshot.getChildren()){
            Booking booking = dataSnapshot.getValue(Booking.class);
            if(booking.getStatus().equals("Accepted")){
              acceptedRoomName = booking.getRoom();
              acceptedStartTime = booking.getStartTime();
              acceptedBookDate = booking.getDate();


              //Iterate throught the roomlist and disable for the accepted room

              for(room room: roomList){
                if(acceptedRoomName.equals(room.getRoom())){
                  Toast.makeText(BookingFormActivity.this, room.getRoom(), Toast.LENGTH_SHORT).show();
                  formAdapter.setDateBook(acceptedBookDate);
                  formAdapter.setDisabledStartTime(acceptedStartTime);
                  formAdapter.setRoomSelected(acceptedRoomName);

                }
              }
              // Notify the adapter that the data has changed

            }
          }
        }
        formAdapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }
}