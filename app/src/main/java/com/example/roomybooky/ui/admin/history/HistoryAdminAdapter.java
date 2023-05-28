package com.example.roomybooky.ui.admin.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.data.user.Booking;
import com.example.roomybooky.R;

import java.util.ArrayList;

public class HistoryAdminAdapter extends RecyclerView.Adapter<HistoryAdminAdapter.ViewHolder> {
  private Context context;
  private ArrayList<Booking> bookedList;
  private Integer resourceId;

  public HistoryAdminAdapter(Context context, ArrayList<Booking> bookedList, Integer resourceId) {
    this.context = context;
    this.bookedList = bookedList;
    this.resourceId = resourceId;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getResourceId(), parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Booking booking = bookedList.get(position);
    holder.name.setText(booking.getUsername());
    holder.room.setText(booking.getRoom());
    holder.dateTxt.setText(booking.getDate());
    holder.timeTxt.setText(booking.getTimeFormat());
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
    public TextView name, room, dateTxt, timeTxt;

    public ViewHolder(View itemView) {
      super(itemView);
      name = (TextView) itemView.findViewById(R.id.nameOfBooking);
      room = (TextView) itemView.findViewById(R.id.roomTitleAdmin);
      dateTxt = (TextView) itemView.findViewById(R.id.dateBookedAdmin);
      timeTxt = (TextView) itemView.findViewById(R.id.timeBookedAdmin);
    }
  }
}
