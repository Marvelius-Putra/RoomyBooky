package com.example.roomybooky.ui.admin.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.ProfileActivity;
import com.example.roomybooky.ui.admin.history.Filter.CompletedAdminFragment;
import com.example.roomybooky.ui.admin.history.Filter.DeclinedAdminFragment;
import com.example.roomybooky.ui.admin.history.Filter.OnGoingAdminFragment;

public class HistoryAdminFragment extends Fragment {
  private RadioGroup radioGroup;
  private RadioButton radioButton;
  private Integer statusType;
  private ImageView btnProfPic;
  private Intent intent;

  private OnGoingAdminFragment onGoingAdminFragment = new OnGoingAdminFragment();
  private CompletedAdminFragment completedAdminFragment = new CompletedAdminFragment();
  private DeclinedAdminFragment declinedAdminFragment = new DeclinedAdminFragment();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_history_admin, container, false);

    // init button profile picture
    btnProfPic = (ImageView) view.findViewById(R.id.profPicImg);
    btnProfPic.setOnClickListener(v -> {
      setIntent(new Intent(getActivity(), ProfileActivity.class));
      startActivity(getIntent());
    });

    // init radio button
    radioGroup = (RadioGroup) view.findViewById(R.id.historyTypeAdmin);
    statusType = radioGroup.getCheckedRadioButtonId();
    radioButton = (RadioButton) view.findViewById(statusType);

    // set radio group click listener
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        Fragment selectedFragment = new Fragment();
        switch (checkedId) {
          case R.id.onGoingStatusAdmin:
            selectedFragment = onGoingAdminFragment;
            break;
          case R.id.completedStatusAdmin:
            selectedFragment = completedAdminFragment;
            break;
          case R.id.declinedStatusAdmin:
            selectedFragment = declinedAdminFragment;
            break;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.containerAdminHistory, selectedFragment);
        transaction.commit();
      }
    });
    radioGroup.check(R.id.onGoingStatusAdmin);

    return view;
  }

  public Intent getIntent() {
    return intent;
  }

  public void setIntent(Intent intent) {
    this.intent = intent;
  }
}