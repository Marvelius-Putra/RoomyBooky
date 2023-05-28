package com.example.roomybooky.ui.admin.request;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.ProfileActivity;

public class RequestAdminFragment extends Fragment {
  private ImageView btnProfPic;
  private Intent intent;
  private RequestChildFragment requestChildFragment;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_request_admin, container, false);

    // init button profile picture;
    btnProfPic = (ImageView) view.findViewById(R.id.profPicImg);
    btnProfPic.setOnClickListener(v -> {
      setIntent(new Intent(getActivity(), ProfileActivity.class));
      startActivity(getIntent());
    });
    requestChildFragment = new RequestChildFragment();
    new Fragment();
    Fragment selectedFragment;
    selectedFragment = requestChildFragment;

    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    transaction.replace(R.id.containerAdminRequest, selectedFragment);
    transaction.commit();
    return view;
  }

  public Intent getIntent() {
    return intent;
  }

  public void setIntent(Intent intent) {
    this.intent = intent;
  }
}