package com.example.roomybooky.UI.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.roomybooky.R;
import com.example.roomybooky.UI.LoginActivity;
import com.example.roomybooky.UI.User.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminProfileActivity extends AppCompatActivity {
  private TextView buttonGoToHome;
  private CardView logoutcard;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_profile);
    logoutcard = findViewById(R.id.logoutCard);

    logoutcard.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(AdminProfileActivity.this, LoginActivity.class);
        startActivity(i);

      }
    });

    // init textview listener (buttonGoToHome)
    buttonGoToHome = (TextView) findViewById(R.id.backToMain);
    buttonGoToHome.setOnClickListener(v -> {
      Intent intent = new Intent(AdminProfileActivity.this, AdminPage.class);
      finish();
      startActivity(intent);
    });
  }
}