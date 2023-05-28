package com.example.roomybooky.ui.user.history.Filter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roomybooky.data.user.Booking;
import com.example.roomybooky.R;
import com.example.roomybooky.ui.user.history.HistoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompletedFragment extends Fragment {
//  declare atribute and connect the adapter
  private RecyclerView recyclerView;
  public ArrayList<Booking> historyList;
  HistoryAdapter historyAdapter;
  Context context;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_completed, container, false);

    Bundle bundle = getArguments();
    String name = bundle.getString("NAME");
    // init recycler view
    this.context = getActivity();
    recyclerView = view.findViewById(R.id.recyclerViewTemplateCompleted);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));

    // init item on recycler view
    historyList = new ArrayList<>();
    historyAdapter = new HistoryAdapter(getContext(), historyList, R.layout.history_completed_list);
    recyclerView.setAdapter(historyAdapter);

//   Read the completed request data in firebase
    DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
    Query query = bookingsRef.orderByChild("date");
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        historyList.clear();
        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
          Booking bookingData = dataSnapshot.getValue(Booking.class);
          if(bookingData.getUsername().equals(name) && bookingData.getStatus().equals("Accepted") ){
            historyList.add(bookingData);
          }
        }
        historyAdapter.notifyDataSetChanged();
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    return view;
  }
}