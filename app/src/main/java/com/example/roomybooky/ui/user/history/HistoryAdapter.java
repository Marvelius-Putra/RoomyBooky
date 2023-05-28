package com.example.roomybooky.ui.user.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.data.user.Booking;
import com.example.roomybooky.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
  private Context context;
  private ArrayList<Booking> bookedList;
  private Integer resourceId;
  String roomName, floor, category, capacity, status, name, startTime, endTime, date;

  public HistoryAdapter(Context context, ArrayList<Booking> bookedList, Integer resourceId) {
    this.context = context;
    this.bookedList = bookedList;
    this.resourceId = resourceId;
  }

  @NonNull
  @Override
  public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getResourceId(), parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
    Booking model = bookedList.get(position);
    holder.roomTitle.setText(model.getRoom());
    holder.timeBooked.setText(model.getTimeFormat());
    holder.dateBooked.setText(model.getDate());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        get the model data
        roomName = model.getRoom();
        floor = String.valueOf(model.getFloor());
        category = model.getCategory();
        status = model.getStatus();
        capacity = String.valueOf(model.getMember());
        name = model.getUsername();
        startTime = model.getStartTime();
        endTime = model.getEndTime();
        date = model.getDate();
        Intent i = new Intent(context, BookingDetails.class);
        i.putExtra("roomName", roomName);
        i.putExtra("floor", floor);
        i.putExtra("category", category);
        i.putExtra("status", status);
        i.putExtra("capacity", capacity);
        i.putExtra("name", name);
        i.putExtra("startTime", startTime);
        i.putExtra("endTime", endTime);
        i.putExtra("date", date);
        context.startActivity(i);
      }
    });
  }

  @Override
  public int getItemCount() {
    return bookedList.size();
  }

  public Integer getResourceId() {
    return resourceId;
  }

  public void setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView roomTitle;
    public TextView dateBooked;
    public TextView timeBooked;

    public ViewHolder(View itemView) {
      super(itemView);
      roomTitle = (TextView) itemView.findViewById(R.id.roomTitle);
      dateBooked = (TextView) itemView.findViewById(R.id.dateBooked);
      timeBooked = (TextView) itemView.findViewById(R.id.timeBooked);
    }
  }
}
