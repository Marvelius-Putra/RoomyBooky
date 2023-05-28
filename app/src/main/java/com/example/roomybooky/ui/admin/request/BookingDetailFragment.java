package com.example.roomybooky.ui.admin.request;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.roomybooky.data.user.Booking;
import com.example.roomybooky.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BookingDetailFragment extends DialogFragment{

    private TextView date, floor, room, starttime,endtime;
    private EditText inputreason, inputnumber;
    private Button btnAcc, btnDecline;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_booking_details, container, false);
        date = rootView.findViewById(R.id.dateOfBookingDetail);
        floor = rootView.findViewById(R.id.floorBookingDetail);
        room = rootView.findViewById(R.id.roomBookingDetail);
        starttime = rootView.findViewById(R.id.starttimeBookingDetail);
        endtime = rootView.findViewById(R.id.endtimeBookingDetail);
        btnAcc = rootView.findViewById(R.id.btnAccept);
        btnDecline = rootView.findViewById(R.id.btnDecline);
        inputreason = rootView.findViewById(R.id.inputReasonField);
        inputnumber = rootView.findViewById(R.id.inputNumberPeopleField);

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Bundle bundle = getArguments();
        Booking model = bundle.getParcelable("BOOKING_DETAIL");

        date.setText(model.getDate());
        floor.setText(String.valueOf(model.getFloor()));
        room.setText(model.getRoom());
        starttime.setText(model.getStartTime());
        endtime.setText(model.getEndTime());
        inputreason.setText(model.getDescription());
        inputnumber.setText(String.valueOf(model.getMember()));
        inputreason.setEnabled(false);
        inputnumber.setEnabled(false);

        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setStatus("Accepted");
                DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference().child("bookings").child(model.getBookingID());
                bookingRef.child("status").setValue("Accepted");
                dismiss();
            }
        });
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setStatus("Declined");
                DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference().child("bookings").child(model.getBookingID());
                bookingRef.child("status").setValue("Declined");
                dismiss();
            }
        });

        super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
