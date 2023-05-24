package com.example.roomybooky.UI.User.Category;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.roomybooky.R;
import com.example.roomybooky.UI.User.BookingFormActivity;
import com.example.roomybooky.UI.User.UserProfileActivity;

public class CategoryFragment extends Fragment {
//    declare the xml Atribut
    ImageView btn_class, btn_lab, btn_sadc, btn_lkc, profilePicture;
    String category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        btn_class = rootView.findViewById(R.id.gobook_class);
        btn_lab = rootView.findViewById(R.id.gobook_lab);
        btn_sadc = rootView.findViewById(R.id.gobook_sadc);
        btn_lkc = rootView.findViewById(R.id.gobook_lkc);
        profilePicture = rootView.findViewById(R.id.profPicImg);

//        class Category method
        btn_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Class";
                Intent i = new Intent(getActivity(), BookingFormActivity.class); // use getActivity() to get the context of the activity
                i.putExtra("category", category); // pass the flag value to the new activity
                startActivity(i);
            }
        });
        //        lab Category method
        btn_lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Lab";
                Intent i = new Intent(getActivity(), BookingFormActivity.class); // use getActivity() to get the context of the activity
                i.putExtra("category", category); // pass the flag value to the new activity
                startActivity(i);
            }
        });
        //        lkc Category method
        btn_lkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "LKC";
                Intent i = new Intent(getActivity(), BookingFormActivity.class); // use getActivity() to get the context of the activity
                i.putExtra("category", category); // pass the flag value to the new activity
                startActivity(i);
            }
        });
        //        SADC Category method
        btn_sadc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "SADC";
                Intent i = new Intent(getActivity(), BookingFormActivity.class); // use getActivity() to get the context of the activity
                i.putExtra("category", category); // pass the flag value to the new activity
                startActivity(i);
            }
        });

        profilePicture.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            startActivity(intent);
        });
        return rootView;
    }
}