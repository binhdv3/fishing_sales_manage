package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binhdv35.fishing_sales_manage.R;

public class SignInActivity extends AppCompatActivity {

    private EditText edUsername, edPassw;
    private Button btnSnI;
    private TextView tvForgotPassw, tvErUsername, tvErPassw;
    private CheckBox chk_remember;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        intID();
        preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        btnSnI.setOnClickListener(v -> {
            String username = edUsername.getText().toString().trim();
            String passw = edPassw.getText().toString().trim();
            reSetVisibale(false);
            checkAccount(username, passw);

        });
    }

    private void checkAccount(String username, String passw) {
        if(validate(username,passw).equals("error_passw"))
        {
            tvErPassw.setVisibility(View.VISIBLE);
            tvErPassw.setText("Sai mật khẩu");
        }else if(validate(username,passw).equals("error_username")) {
            tvErUsername.setVisibility(View.VISIBLE);
            tvErUsername.setText("Sai tên đăng nhập");
        } else if (validate(username,passw).equals("empty_username")) {
            tvErUsername.setVisibility(View.VISIBLE);
            tvErUsername.setText("Không được để trống");
        } else if (validate(username,passw).equals("empty_passw")) {
            tvErPassw.setVisibility(View.VISIBLE);
            tvErPassw.setText("Không được để trống");
        } else {
            //Đăng nhập thành công-----------------------
            Intent intent = new Intent(this, MainActivity.class);
            writePrefer(username,passw, chk_remember.isChecked());
            intent.putExtra("USER_NAME", username);
            startActivity(intent);
            finish();
        }
    }

    //preferences------------
    private void writePrefer(String username, String passw, Boolean status){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USERNAME", username);
        editor.putString("PASSWORD", passw);
        editor.putBoolean("STATUS", status);
        editor.commit();
    }

    private void readPrefer(){
        String strUserName = preferences.getString("USERNAME", null);
        String strPassW = preferences.getString("PASSWORD", null);
        Boolean status = preferences.getBoolean("STATUS", false);

        chk_remember.setChecked(status);
        if(chk_remember.isChecked() == true)
        {
            edUsername.setText(strUserName);
            edPassw.setText(strPassW);
        }
    }
    //--------------

    private void reSetVisibale(boolean tf) {
        if(tf == true){
            tvErPassw.setVisibility(View.VISIBLE);
            tvErUsername.setVisibility(View.VISIBLE);
        }
        else {
            tvErUsername.setVisibility(View.INVISIBLE);
            tvErPassw.setVisibility(View.INVISIBLE);
        }
    }

    private void intID() {
        edUsername = findViewById(R.id.signin_ed_username);
        edPassw = findViewById(R.id.signin_ed_passw);
        btnSnI = findViewById(R.id.signin_btn_sgi);
        tvForgotPassw = findViewById(R.id.signin_tv_forgot_passw);
        tvErPassw = findViewById(R.id.signin_tv_error_passw);
        tvErUsername = findViewById(R.id.signin_tv_error_username);
        chk_remember = findViewById(R.id.chk_remember);
    }

    private String validate(String username, String passw){
        if(!username.equals("Binh") && !username.isEmpty())
            return "error_username";
        if(!passw.equals("123") && !passw.isEmpty())
            return "error_passw";
        if(username.isEmpty())
            return "empty_username";
        if (passw.isEmpty())
            return "empty_passw";

        return "no_error"; //khônh có lỗi nào
    }

    @Override
    protected void onStart() {
        super.onStart();
        readPrefer();
        tvErUsername.setVisibility(View.INVISIBLE);
        tvErPassw.setVisibility(View.INVISIBLE);
    }
}