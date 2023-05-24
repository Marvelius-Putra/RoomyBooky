package com.example.roomybooky.UI.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.roomybooky.R;
import com.example.roomybooky.UI.HomePage;
import com.example.roomybooky.UI.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {
  private TextView buttonGoToHome, txtUsername, txtstudentId, logout;
  private CardView logoutcard;

  private String username;
  private FirebaseAuth firebaseAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);
    txtUsername = findViewById(R.id.userName);
    txtstudentId = findViewById(R.id.admin_role_text);
    logout = findViewById(R.id.logout_text_admin);
    logoutcard = findViewById(R.id.logoutCard);
    Intent i = getIntent();
    username = i.getStringExtra("name");

    txtUsername.setText(username);

    logoutcard.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(UserProfileActivity.this, LoginActivity.class);
        startActivity(i);

      }
    });

    // init textview listener (buttonGoToHome)
    buttonGoToHome = (TextView) findViewById(R.id.backToMain);
    buttonGoToHome.setOnClickListener(v -> {
      Intent intent = new Intent(UserProfileActivity.this, HomePage.class);
      finish();
      startActivity(intent);
    });
  }
}