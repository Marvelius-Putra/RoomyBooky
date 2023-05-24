package com.example.roomybooky.UI.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.roomybooky.R;
import com.example.roomybooky.UI.HomePage;

public class ConfirmFragment extends androidx.fragment.app.DialogFragment {
  private ImageView btnClose;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_confirm, container, false);
    getDialog().setCanceledOnTouchOutside(true);

    // init button close
    btnClose = (ImageView) view.findViewById(R.id.buttonClose);
    btnClose.setOnClickListener(v -> {
      onDismiss(getDialog());
    });

    return view;
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
    Intent i = new Intent(getActivity(), HomePage.class);
    startActivity(i);
  }
}