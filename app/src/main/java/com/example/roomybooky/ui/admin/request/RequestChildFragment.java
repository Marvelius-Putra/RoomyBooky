package com.example.roomybooky.ui.admin.request;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.data.user.Booking;
import com.example.roomybooky.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestChildFragment extends Fragment implements RequestAdminAdapter.ItemClickInterface {
  public ArrayList<Booking> pendingList;
  RequestAdminAdapter requestAdminAdapter;
  Context context;
  RecyclerView RequestRecyclerView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_request_child, container, false);
    this.context =getActivity();
    RequestRecyclerView = rootView.findViewById(R.id.recyclerViewRequestAdmin);
    RequestRecyclerView.setHasFixedSize(true);
    RequestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    pendingList = new ArrayList<>();
    DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
    Query query = bookingsRef.orderByChild("date");
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        pendingList.clear();
        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
          Booking bookingData = dataSnapshot.getValue(Booking.class);
          if(bookingData.getStatus().equals("Pending")){
            pendingList.add(bookingData);
          }
        }
        requestAdminAdapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    requestAdminAdapter = new RequestAdminAdapter(context, pendingList, this);
    RequestRecyclerView.setAdapter(requestAdminAdapter);
    return rootView;
  }
  @Override
  public void onItemClick(int pos) {
    displayFrag(pendingList.get(pos));
  }

  private void displayFrag(Booking model){
    BookingDetailFragment dialogFragment = new BookingDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable("BOOKING_DETAIL", model);
    dialogFragment.setArguments(bundle);
    dialogFragment.show(getChildFragmentManager(),"MyFragment");
  }

}