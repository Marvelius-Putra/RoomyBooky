package com.example.roomybooky;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminPage extends AppCompatActivity {

    //instantiate an object of the fragment here
    AdminFragment adminFragment = new AdminFragment();
    RequestFragment requestFragment = new RequestFragment();

    //instantiate the rest of the objects here
    BottomNavigationView bottomNavigationView;

    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.BottomNavID);

        //replace the FrameLayout with fragment object instantiated above
        getSupportFragmentManager().beginTransaction().replace(R.id.containerID, adminFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //follow this template with the rest of the fragments on the navbar
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,adminFragment).commit();
                        return true;
                    case R.id.Category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,requestFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}