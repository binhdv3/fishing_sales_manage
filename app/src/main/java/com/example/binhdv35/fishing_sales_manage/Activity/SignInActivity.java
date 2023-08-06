package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private EditText edUsername, edPassw;
    private Button btnSnI;
    private TextView tvForgotPassw, tvErUsername, tvErPassw;
    private CheckBox chk_remember;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private List<Account> accountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        intID();
        settingPDialog();
        getDataAccount();
        accountList = Account.accountList;
        preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        btnSnI.setOnClickListener(v -> {
            String username = edUsername.getText().toString().trim();
            String passw = edPassw.getText().toString().trim();
            reSetVisibale(false);
            checkAccount(username, passw);
        });
    }

    private void settingPDialog() {
        progressDialog = new ProgressDialog(SignInActivity.this);
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

    public static String USERNAME_="";

    private void getDataAccount() {
        showPDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLJson.KEY_GET_ACCOUNT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length() ; i++) {
                                JSONObject account = (JSONObject) response.get(i);
                                String id = account.getString("_id");
                                String id_customer = account.getString("id_customer");
                                String password = account.getString("password");
                                String account_name = account.getString("account_name");
                                Account.accountList.add(
                                        new Account(id,account_name,password,id_customer));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(SignInActivity.this, "Error" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        hidePDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, "Error" + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                hidePDialog();
            }
        });

        RequestQueueController.getInstance().addToRequestQueue(jsonArrayRequest);
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
        } else if(validate(username,passw).equals("successfully")){
            //Đăng nhập thành công-----------------------
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            writePrefer(username,passw, chk_remember.isChecked());
            intent.putExtra("USER_NAME", username);
            USERNAME_ = username;
            startActivity(intent);
            finish();
        }
    }


    public static String ID_CUSTOMER = "";
    //validate----------------------
    private String validate(String username, String passw){
        String result="";
        for (int i=0; i < accountList.size(); i++){
            Account account = accountList.get(i);
            if(!username.equals(account.getAccountName()) && !username.isEmpty())
                result = "error_username";
            if(!passw.equals(account.getPassWord()) && !passw.isEmpty())
                result = "error_passw";
            if(username.equals(account.getAccountName()) && passw.equals(account.getPassWord())){
                ID_CUSTOMER = account.getIdCustomer();
                return "successfully";
            }
        }
        if(username.isEmpty())
            return "empty_username";
        if (passw.isEmpty())
            return "empty_passw";
        return result; //khônh có lỗi nào
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

    @Override
    protected void onStart() {
        super.onStart();
        readPrefer();
        tvErUsername.setVisibility(View.INVISIBLE);
        tvErPassw.setVisibility(View.INVISIBLE);
    }
}