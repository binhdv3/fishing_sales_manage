package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText edUserName, edAdress, edPhoneNumber, edEmail, edImage;
    private Button btnNextToUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUi();
        btnNextToUp.setOnClickListener(v -> {
            addNewUser();
        });

    }

    private void addNewUser() {
        String name = edUserName.getText().toString().trim();
        String adress = edAdress.getText().toString().trim();
        String phoneNumber = edPhoneNumber.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String image = edImage.getText().toString().trim();

        JSONObject customerJson = new JSONObject();
        try {
            customerJson.put("name" , name);
            customerJson.put("adress" , adress);
            customerJson.put("phone_number" , phoneNumber);
            customerJson.put("email", email);
            customerJson.put("image", image);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLJson.KEY_POST_CUSTOMER, customerJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getContext(),
//                                    "Error: "+ error.getMessage() , Toast.LENGTH_SHORT).show();
                        Log.d("json_error" , error.getMessage());
                    }
                }
        );

        RequestQueueController.getInstance().addToRequestQueue(request);
        Toast.makeText(this, "Thêm thông tin khách hàng thành công !", Toast.LENGTH_SHORT).show();
        //Chuyển đến trang kế tiếp
        Intent intent = new Intent(SignUpActivity.this, SignUpAccountActivity.class);
        startActivity(intent);
        finish();
    }

    private void initUi() {
        edUserName = findViewById(R.id.signup_ed_username);
        edAdress = findViewById(R.id.signup_ed_adress);
        edPhoneNumber = findViewById(R.id.signup_ed_phone_number);
        edEmail = findViewById(R.id.signup_ed_email);
        edImage = findViewById(R.id.signup_ed_image);
        btnNextToUp = findViewById(R.id.signup_btn_next);
    }
}