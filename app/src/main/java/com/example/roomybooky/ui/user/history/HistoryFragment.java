package com.example.roomybooky.ui.user.history;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.user.history.Filter.CompletedFragment;
import com.example.roomybooky.ui.user.history.Filter.DeclinedFragment;
import com.example.roomybooky.ui.user.history.Filter.OnGoingFragment;
import com.example.roomybooky.ui.ProfileActivity;

public class HistoryFragment extends Fragment {
  private RadioGroup radioGroup;
  private RadioButton radioButton;
  private Integer statusType;
  private ImageView profPicImg;
  private OnGoingFragment onGoingFragment;
  private CompletedFragment completedFragment;
  private DeclinedFragment declinedFragment;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_history, container, false);

    // init profile image
    profPicImg = (ImageView) view.findViewById(R.id.profPicImg);
    profPicImg.setOnClickListener(v -> {
      Intent intent = new Intent(getActivity(), ProfileActivity.class);
      startActivity(intent);
    });

    // init radio button
    radioGroup = (RadioGroup) view.findViewById(R.id.historyType);
    statusType = radioGroup.getCheckedRadioButtonId();
    radioButton = (RadioButton) view.findViewById(statusType);

//    get the username data from homepage
    Bundle bundle = getArguments();
    String name = bundle.getString("NAME");

    // fragment list
    onGoingFragment = new OnGoingFragment();
    completedFragment = new CompletedFragment();
    declinedFragment = new DeclinedFragment();



    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        Fragment selectedFragment = new Fragment();
//        method for user click fragment ongoing,completed, or declined
        switch (checkedId) {
          case R.id.onGoingStatus:
            Bundle bundle = new Bundle();
            bundle.putString("NAME", name); // Pass the name variable as an argument in the bundle
            onGoingFragment.setArguments(bundle); // Pass the bundle to the historyFragment
            selectedFragment = onGoingFragment;
            break;
          case R.id.completedStatus:
            bundle = new Bundle();
            bundle.putString("NAME", name); // Pass the name variable as an argument in the bundle
            completedFragment.setArguments(bundle); // Pass the bundle to the historyFragment
            selectedFragment = completedFragment;
            break;
          case R.id.declinedStatus:
            bundle = new Bundle();
            bundle.putString("NAME", name); // Pass the name variable as an argument in the bundle
            declinedFragment.setArguments(bundle); // Pass the bundle to the historyFragment
            selectedFragment = declinedFragment;
            break;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.containerHistory, selectedFragment);
        transaction.commit();
      }
    });

    radioGroup.check(R.id.onGoingStatus);

    return view;
  }

//  pop up fragment
  private void showChildFragment(Fragment fragment) {
    FragmentManager fragmentManager = getChildFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.containerHistory, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
}