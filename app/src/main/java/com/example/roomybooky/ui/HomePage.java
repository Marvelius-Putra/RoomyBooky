package com.example.roomybooky.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.roomybooky.R;
import com.example.roomybooky.ui.user.category.CategoryFragment;
import com.example.roomybooky.ui.user.history.HistoryFragment;
import com.example.roomybooky.ui.user.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {
    private FirebaseAuth fAuth;
    //instantiate an object of the fragment here
    HomeFragment homeFragment = new HomeFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    HistoryFragment historyFragment = new HistoryFragment();

    //instantiate the rest of the objects here
    BottomNavigationView bottomNavigationView;

    private String name, userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.BottomNavID);

        // get user data
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = reference.child("users");
        Query query = ref.orderByChild("userUID").equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        name = ds.child("name").getValue().toString();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

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
                    case R.id.Category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,categoryFragment).commit();
                        return true;
                    case R.id.History:
                        Bundle bundle = new Bundle();
                        bundle.putString("NAME", name);
                        historyFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerID,historyFragment).commit();
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