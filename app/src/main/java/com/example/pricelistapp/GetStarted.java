package com.example.pricelistapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.pricelistapp.Home.HomeActivity;

public class GetStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void getStartButton(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(GetStarted.this, HomeActivity.class));
                finish();
            }
        },500);
    }
}