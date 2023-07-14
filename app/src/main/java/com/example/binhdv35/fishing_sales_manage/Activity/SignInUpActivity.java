package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.binhdv35.fishing_sales_manage.R;

public class SignInUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignin, btnSignup;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        initID();

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    private void initID() {
        btnSignin = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_signup);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_signin){
            intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_signup) {
            intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }

}