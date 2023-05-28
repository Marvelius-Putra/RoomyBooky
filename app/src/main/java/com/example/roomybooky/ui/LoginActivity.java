package com.example.roomybooky.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.admin.AdminPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private Button submit;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.IDuser);
        pass = findViewById(R.id.IDpass);
        progressBar = findViewById(R.id.IDprogress);
        submit = findViewById(R.id.IDsubmitbtn);
        fAuth = FirebaseAuth.getInstance();

        if(firebaseUser != null){
            Intent i = new Intent(LoginActivity.this, AdminPage.class);
            //i.putExtra("USER", username);
            startActivity(i);
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String username = user.getText().toString();
                String password = pass.getText().toString();
                verification(username, password);
            }
        });
    }

    public void verification(String username, String password){
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please fill in the required fields!", Toast.LENGTH_SHORT).show();
        }else{
            fAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        if(username.equals("admin@gmail.com")){
                            Intent i = new Intent(LoginActivity.this, AdminPage.class);
                            //i.putExtra("USER", username);
                            startActivity(i);
                            finish();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Intent i = new Intent(LoginActivity.this, HomePage.class);
                            //i.putExtra("USER", username);
                            startActivity(i);
                            finish();
                        }

                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}