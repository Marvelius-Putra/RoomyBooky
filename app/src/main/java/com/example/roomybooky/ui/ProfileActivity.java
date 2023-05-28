package com.example.roomybooky.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roomybooky.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class ProfileActivity extends AppCompatActivity implements Serializable {
  private TextView buttonGoToHome, txtUsername, txtRole, txtNim, txtNote, logout;
  private TextView buttonPrivpol, buttonTermsCondition, buttonHelp;
  private ImageView profilePicture;
  private CardView logoutcard;

  private String username, userID;
  private FirebaseAuth firebaseAuth;

  private DatabaseReference reference, ref;
  private Query query;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    // initialize
    txtUsername = findViewById(R.id.userName);
    txtRole = findViewById(R.id.role_text);
    txtNim = findViewById(R.id.nim_text);
    txtNote = findViewById(R.id.notes_text);
    logout = findViewById(R.id.logout_text_admin);
    logoutcard = findViewById(R.id.logoutCard);
    profilePicture = findViewById(R.id.userProfpic);
    userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    // get user data
    reference = FirebaseDatabase.getInstance().getReference();
    ref = reference.child("users");
    query = ref.orderByChild("userUID").equalTo(userID);
    setProfileComponent(query);

    // logout
    logoutcard.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(i);
      }
    });

    // init textview listener (buttonGoToHome)
    buttonGoToHome = (TextView) findViewById(R.id.backToMain);
    buttonGoToHome.setOnClickListener(v -> {
      Intent intent = new Intent(ProfileActivity.this, HomePage.class);
      finish();
      startActivity(intent);
    });

    // init textview listener (buttonPrivpol)
    buttonPrivpol = (TextView) findViewById(R.id.privpol_text_admin);
    buttonPrivpol.setOnClickListener(v -> {
      Intent intent = new Intent(ProfileActivity.this, WebActivity.class);
      intent.putExtra("LABEL", "PRIVPOL");
      finish();
      startActivity(intent);
    });

    // init textview listener (buttonPrivpol)
    buttonTermsCondition = (TextView) findViewById(R.id.terms_text_admin);
    buttonTermsCondition.setOnClickListener(v -> {
      Intent intent = new Intent(ProfileActivity.this, WebActivity.class);
      intent.putExtra("LABEL", "TERMS");
      finish();
      startActivity(intent);
    });

    // init textview listener (buttonHelp)
    buttonHelp = (TextView) findViewById(R.id.helps_text_admin);
    buttonHelp.setOnClickListener(v -> {
      Intent intent = new Intent(ProfileActivity.this, WebActivity.class);
      intent.putExtra("LABEL", "HELP");
      finish();
      startActivity(intent);
    });
  }

  public void setProfileComponent(Query query) {
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
          for(DataSnapshot ds : snapshot.getChildren()){
            txtUsername.setText(ds.child("name").getValue().toString());
            txtRole.setText(ds.child("role").getValue().toString());
            txtNim.setText("(" + ds.child("nim").getValue().toString() + ")");
            if (ds.child("role").getValue().toString().equals("Admin")) {
              profilePicture.setImageResource(R.drawable.profpic);
              txtNote.setText("BINUS BANDUNG");
            }
          }
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        throw error.toException();
      }
    });
  }
}