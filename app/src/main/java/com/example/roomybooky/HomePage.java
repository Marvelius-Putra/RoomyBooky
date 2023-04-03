package com.example.roomybooky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    //instantiate an object of the fragment here
    HomeFragment homeFragment = new HomeFragment();

    //instantiate the rest of the objects here
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.BottomNavID);

        //replace the FrameLayout with fragment object instantiated above
        getSupportFragmentManager().beginTransaction().replace(R.id.containerID, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //follow this template with the rest of the fragments on the navbar
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,homeFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}