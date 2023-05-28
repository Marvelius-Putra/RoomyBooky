package com.example.roomybooky.ui.admin.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.roomybooky.Data.Admin.RecyclerViewInterface;
import com.example.roomybooky.data.user.Booking;
import com.example.roomybooky.R;

import java.util.ArrayList;

public class RequestAdminAdapter extends RecyclerView.Adapter<RequestAdminAdapter.ViewHolder> {
  private Context context;
  private ArrayList<Booking> bookingPending;
  private String nameTxt, roomTxt, dateTxt, timeTxt;
  private ItemClickInterface itemClickInterface;

//  private static final RecyclerViewInterface recyclerViewInterface = null;


  public RequestAdminAdapter(Context context, ArrayList<Booking> bookingPending, ItemClickInterface itemClickInterface) {
    this.context = context;
    this.bookingPending = bookingPending;
    this.itemClickInterface = itemClickInterface;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_request_list, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Booking model = bookingPending.get(position);
    holder.name.setText(model.getUsername());
    holder.room.setText(model.getRoom());
    holder.date.setText(model.getDate());
    holder.time.setText(model.getStartTime() + " - " + model.getEndTime());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        itemClickInterface.onItemClick(position);
        notifyDataSetChanged();
//        bookingPending.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, getItemCount());
      }
    });
  }

  @Override
  public int getItemCount() {
    return bookingPending.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name, room, date, time;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.nameOfBooking);
      room = itemView.findViewById(R.id.roomTitle);
      date = itemView.findViewById(R.id.dateTitle);
      time = itemView.findViewById(R.id.timeTitle);
    }
  }
  public interface ItemClickInterface{
    void onItemClick(int pos);
  }
}
