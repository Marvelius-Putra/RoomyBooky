package com.example.roomybooky.ui.user.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.HomePage;
import com.example.roomybooky.ui.user.category.CategoryFragment;
import com.example.roomybooky.ui.user.history.HistoryFragment;
import com.example.roomybooky.ui.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    TextView username;
    ImageView bookroom, bookhistory, profilePicture;
    String userID;
    CategoryFragment categoryFragment = new CategoryFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    private FirebaseAuth fAuth;
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        username = rootView.findViewById(R.id.homeUserID);
        bookroom = rootView.findViewById(R.id.bookRoom);
        bookhistory = rootView.findViewById(R.id.bookHistory);
        profilePicture = rootView.findViewById(R.id.profPicImg);



        // get user data
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = reference.child("users");
        Query query = ref.orderByChild("userUID").equalTo(userID);

//        get the user login data
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        name = ds.child("name").getValue().toString();
                        username.setText(ds.child("name").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        //        method when user click the book room btn
        bookroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage homePage = (HomePage) getActivity();
                FragmentManager fragmentManager = getParentFragmentManager();
                Fragment existingFragment = fragmentManager.findFragmentByTag("categoryFragment");

                if (existingFragment == null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerID, categoryFragment, "categoryFragment");
                    fragmentTransaction.commit();

                } else {
                    fragmentManager.popBackStackImmediate("categoryFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                homePage.selectCategory();
            }
        });

//        method when user click the book history btn
        bookhistory.setOnClickListener(v -> {
            HomePage homePage = (HomePage) getActivity();
            FragmentManager fragmentManager = getParentFragmentManager();
            Fragment existingFragment = fragmentManager.findFragmentByTag("historyFragment");

            if (existingFragment == null) {
                Bundle bundle = new Bundle();
                bundle.putString("NAME", name); // Pass the name variable as an argument in the bundle
                historyFragment.setArguments(bundle); // Pass the bundle to the historyFragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerID, historyFragment, "historyFragment");
                fragmentTransaction.commit();

            } else {
                fragmentManager.popBackStackImmediate("historyFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            homePage.selectHistory();
        });


        profilePicture.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });

        return rootView;
    }
}