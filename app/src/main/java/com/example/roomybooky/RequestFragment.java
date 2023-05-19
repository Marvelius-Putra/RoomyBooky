package com.example.roomybooky;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roomybooky.models.booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RequestFragment extends Fragment {
    public ArrayList<booking> requestList;
    RequestAdapter requestAdapter;
    Context context;
    RecyclerView RequestRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_request, container, false);
        this.context = getActivity();
        RequestRecyclerView = rootView.findViewById(R.id.requestRecycle);
        RequestRecyclerView.setHasFixedSize(true);
        RequestRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        requestList = new ArrayList<>();
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
        Query query = bookingsRef.orderByChild("date");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    booking bookingData = dataSnapshot.getValue(booking.class);
                    if(bookingData.getStatus().equals("Pending")){
                        requestList.add(bookingData);
                    }
                }
                requestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestAdapter = new RequestAdapter(context, requestList);
        RequestRecyclerView.setAdapter(requestAdapter);
        return rootView;
    }
}