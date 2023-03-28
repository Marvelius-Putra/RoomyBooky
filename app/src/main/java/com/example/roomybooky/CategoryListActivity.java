package com.example.roomybooky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CategoryListActivity extends AppCompatActivity {
    ImageView btn_class, btn_lab, btn_sadc, btn_lkc;
    Integer flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        btn_class = findViewById(R.id.gobook_class);
        btn_lab = findViewById(R.id.gobook_lab);
        btn_sadc = findViewById(R.id.gobook_sadc);
        btn_lkc = findViewById(R.id.gobook_lkc);

        btn_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                Intent i = new Intent(CategoryListActivity.this, FormActivity.class);
                i.putExtra("flag", flag);
                startActivity(i);
            }
        });

        btn_lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                Intent i = new Intent(CategoryListActivity.this, FormActivity.class);
                i.putExtra("flag", flag);
                startActivity(i);
            }
        });

        btn_sadc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 3;
                Intent i = new Intent(CategoryListActivity.this, FormActivity.class);
                i.putExtra("flag", flag);
                startActivity(i);
            }
        });

        btn_lkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 4;
                Intent i = new Intent(CategoryListActivity.this, FormActivity.class);
                i.putExtra("flag", flag);
                startActivity(i);
            }
        });
    }
}