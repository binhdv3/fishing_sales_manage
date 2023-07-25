package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.adapter.ProductAdapter;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Customer;
import com.example.binhdv35.fishing_sales_manage.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUpAccountActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText edAccountName, edAccountPass, edAccountRePass;
    private TextView tvErRepass;
    public static List<Customer> customerList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_account);
        initUi();
        settingPDialog();
        customerList = new ArrayList<>();
        getIdCustomer();

        btnSignUp.setOnClickListener(v -> {
            addNewAccount();
        });
    }

    private void addNewAccount() {
        String accountName = edAccountName.getText().toString().trim();
        String pass = edAccountPass.getText().toString().trim();
        String repass = edAccountRePass.getText().toString().trim();

        JSONObject accountJson = new JSONObject();
        try {
            accountJson.put("id_customer" , customerList.get(customerList.size()-1).getId());
            accountJson.put("account_name" , accountName);
            accountJson.put("password" , pass);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                URLJson.KEY_POST_ACCOUNT, accountJson,
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

        if (!pass.equals(repass)){
            Toast.makeText(this, "Xác nhận mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
        }else {
            RequestQueueController.getInstance().addToRequestQueue(request);
            Toast.makeText(this, "Thêm tài khoản thành công !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpAccountActivity.this, SignInActivity.class);
            startActivity(intent);
        }
    }

    private void getIdCustomer() {
        showPDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLJson.KEY_GET_CUSTOMER,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d("response", response.toString()); // dữ liệu trả về
                        try {
                            for (int i = 0; i < response.length() ; i++) {
                                JSONObject customer = (JSONObject) response.get(i);
                                String id = customer.getString("_id");
                                String name = customer.getString("name");
                                String adress = customer.getString("adress");
                                String phoneNumber = customer.getString("phone_number");
                                String email = customer.getString("email");
                                String image = customer.getString("image");

                                customerList.add(new Customer(id,name,adress,phoneNumber,email,image));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Error" + e.getMessage(),
//                                    Toast.LENGTH_SHORT).show();
                        }
                        hidePDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
            }
        });

        RequestQueueController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    private void settingPDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }
    private void showPDialog() {
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void hidePDialog(){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void initUi() {
        btnSignUp = findViewById(R.id.signup_ac_btn_up);
        edAccountName = findViewById(R.id.signup_ac_ed_account_name);
        edAccountPass = findViewById(R.id.signup_ac_ed_account_pass);
        edAccountRePass = findViewById(R.id.signup_ac_ed_account_repass);
        tvErRepass = findViewById(R.id.signup_tv_error_repassw);
    }
}