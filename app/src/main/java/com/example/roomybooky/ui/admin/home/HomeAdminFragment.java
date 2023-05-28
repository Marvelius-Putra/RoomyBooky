package com.example.roomybooky.ui.admin.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.ProfileActivity;
import com.example.roomybooky.ui.admin.AdminPage;
import com.example.roomybooky.ui.admin.history.HistoryAdminFragment;
import com.example.roomybooky.ui.admin.request.RequestAdminFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeAdminFragment extends Fragment {
  private ImageView btnProfPic, btnRequest, btnHistory;
  private TextView username;
  private Intent intent;
  private String userID;

  private RequestAdminFragment requestAdminFragment = new RequestAdminFragment();
  private HistoryAdminFragment historyAdminFragment = new HistoryAdminFragment();


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_admin, container, false);

    // init text view name
    username = (TextView) view.findViewById(R.id.homeAdminID);
    userID = FirebaseAuth.getInstance().getUid();

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
            String name = ds.child("name").getValue().toString();
            username.setText(ds.child("name").getValue().toString());
          }
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        throw error.toException();
      }
    });

    // init button profile picture;
    btnProfPic = (ImageView) view.findViewById(R.id.profPicImg);
    btnProfPic.setOnClickListener(v -> {
      setIntent(new Intent(getActivity(), ProfileActivity.class));
      startActivity(getIntent());
    });
    AdminPage adminPage = (AdminPage) getActivity();


    // init button request
    btnRequest = (ImageView) view.findViewById(R.id.btnAdminRequest);
    btnRequest.setOnClickListener(v -> {

      FragmentManager fragmentManager = getParentFragmentManager();
      Fragment existingFragment = fragmentManager.findFragmentByTag("categoryFragment");

      if (existingFragment == null) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerID, requestAdminFragment, "requestFragment");
        fragmentTransaction.commit();
      } else {
        fragmentManager.popBackStackImmediate("requestFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
      }
      adminPage.selectCategory();
    });

    // init button history
    btnHistory = (ImageView) view.findViewById(R.id.btnAdminHistory);
    btnHistory.setOnClickListener(v -> {
      FragmentManager fragmentManager = getParentFragmentManager();
      Fragment existingFragment = fragmentManager.findFragmentByTag("categoryFragment");

      if (existingFragment == null) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerID, historyAdminFragment, "historyFragment");
        fragmentTransaction.commit();
      } else {
        fragmentManager.popBackStackImmediate("historyFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
      }
      adminPage.selectHistory();
    });

    return view;
  }

  public Intent getIntent() {
    return intent;
  }

  public void setIntent(Intent intent) {
    this.intent = intent;
  }

}