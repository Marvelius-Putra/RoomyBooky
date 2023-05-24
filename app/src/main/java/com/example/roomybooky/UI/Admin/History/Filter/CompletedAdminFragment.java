package com.example.roomybooky.UI.Admin.History.Filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.Data.User.Booking;
import com.example.roomybooky.R;
import com.example.roomybooky.UI.Admin.History.HistoryAdminAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompletedAdminFragment extends Fragment {
  private Context context;
  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  private ArrayList<Booking> historyCompleted;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_completed_admin, container, false);

    // init recycler view
    this.context = getActivity();
    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAdminCompleted);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    // init item on recycler view
    historyCompleted = new ArrayList<>();
    DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
    Query query = bookingsRef.orderByChild("date");
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        historyCompleted.clear();
        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
          Booking bookingData = dataSnapshot.getValue(Booking.class);
          if(bookingData.getStatus().equals("Accepted")) historyCompleted.add(bookingData);
        }
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
      }
    });

    adapter = new HistoryAdminAdapter(getContext(), historyCompleted, R.layout.history_admin_completed);
    recyclerView.setAdapter(adapter);

    return view;
  }
}