package com.example.roomybooky.ui.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.admin.history.HistoryAdminFragment;
import com.example.roomybooky.ui.admin.home.HomeAdminFragment;
import com.example.roomybooky.ui.admin.request.RequestAdminFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminPage extends AppCompatActivity {

    //instantiate an object of the fragment here
    private HomeAdminFragment homeAdminFragment = new HomeAdminFragment();
    private RequestAdminFragment requestAdminFragment = new RequestAdminFragment();
    private HistoryAdminFragment historyAdminFragment = new HistoryAdminFragment();

    //instantiate the rest of the objects here
    public BottomNavigationView bottomNavigationView;

    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.BottomNavID);

        //replace the FrameLayout with fragment object instantiated above
        getSupportFragmentManager().beginTransaction().replace(R.id.containerID, homeAdminFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //follow this template with the rest of the fragments on the navbar
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,homeAdminFragment).commit();
                        return true;
                    case R.id.Category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,requestAdminFragment).commit();
                        return true;
                    case R.id.History:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,historyAdminFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
    public void selectCategory() {
        bottomNavigationView.setSelectedItemId(R.id.Category);
    }

    public void selectHistory() {
        bottomNavigationView.setSelectedItemId(R.id.History);
    }
}