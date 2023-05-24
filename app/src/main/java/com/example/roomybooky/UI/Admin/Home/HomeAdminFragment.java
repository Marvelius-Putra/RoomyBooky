package com.example.roomybooky.UI.Admin.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roomybooky.R;
import com.example.roomybooky.UI.Admin.AdminPage;
import com.example.roomybooky.UI.Admin.AdminProfileActivity;
import com.example.roomybooky.UI.Admin.History.HistoryAdminFragment;
import com.example.roomybooky.UI.Admin.Request.RequestAdminFragment;
import com.example.roomybooky.UI.HomePage;

public class HomeAdminFragment extends Fragment {
  private ImageView btnProfPic, btnRequest, btnHistory;
  private TextView name;
  private Intent intent;

  private RequestAdminFragment requestAdminFragment = new RequestAdminFragment();
  private HistoryAdminFragment historyAdminFragment = new HistoryAdminFragment();


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_admin, container, false);

    // init text view name
    name = (TextView) view.findViewById(R.id.homeAdminID);
    name.setText("Admin");

    // init button profile picture;
    btnProfPic = (ImageView) view.findViewById(R.id.profPicImg);
    btnProfPic.setOnClickListener(v -> {
      setIntent(new Intent(getActivity(), AdminProfileActivity.class));
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