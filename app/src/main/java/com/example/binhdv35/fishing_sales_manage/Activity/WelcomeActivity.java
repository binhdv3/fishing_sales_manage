package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.binhdv35.fishing_sales_manage.R;

public class WelcomeActivity extends AppCompatActivity {

    private static int TIME_OUT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.hide();
        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, SignInUpActivity.class);
                startActivity(intent);
                finish();
            }
        },TIME_OUT);
    }
}