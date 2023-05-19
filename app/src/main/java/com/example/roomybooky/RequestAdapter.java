package com.example.roomybooky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomybooky.models.booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {
    public Context context;
    public ArrayList<booking> requestList;

    public RequestAdapter(Context context, ArrayList<booking> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_component, parent, false);
        return new RequestHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
        booking model = requestList.get(position);
        holder.user_name.setText(model.getUsername());
        holder.user_room.setText(model.getRoom());
        holder.user_date.setText(model.getDate());
        holder.user_time.setText(model.getStartTime() + " - " + model.getEndTime());



        // Handle "Accept" button click
        holder.btn_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the booking status to "Accepted"
                model.setStatus("Accepted");
                DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference().child("bookings").child(model.getBookingID());
                bookingRef.child("status").setValue("Accepted");

                notifyDataSetChanged();

                // TODO: Update the room status based on user request time to false
                // You can access the room details using the room ID from the booking
                // model.getRoom() will give you the room ID

                // Remove the room from the display page
                requestList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });

        // Handle "Decline" button click
        holder.btn_decl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the booking status to "Declined"
                model.setStatus("Accepted");
                DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference().child("bookings").child(model.getBookingID());
                bookingRef.child("status").setValue("Declined");
                notifyDataSetChanged();
                // Remove the room from the display page
                requestList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class RequestHolder extends RecyclerView.ViewHolder{
        TextView user_name, user_room, user_date, user_time, btnBookDetail;
        Button btn_acc, btn_decl;
        public RequestHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.txt_username);
            user_room = itemView.findViewById(R.id.txt_roomName);
            user_date = itemView.findViewById(R.id.txt_date);
            user_time = itemView.findViewById(R.id.txt_time);
            btnBookDetail = itemView.findViewById(R.id.btn_bookDetails);
            btn_acc = itemView.findViewById(R.id.btn_acc);
            btn_decl = itemView.findViewById(R.id.btn_decl);
        }
    }
}
