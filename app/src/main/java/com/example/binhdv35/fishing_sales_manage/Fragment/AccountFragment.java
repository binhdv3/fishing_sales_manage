package com.example.binhdv35.fishing_sales_manage.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.binhdv35.fishing_sales_manage.Activity.MainActivity;
import com.example.binhdv35.fishing_sales_manage.Activity.SignInActivity;
import com.example.binhdv35.fishing_sales_manage.Activity.SignInUpActivity;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.adapter.ProductAdapter;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Account;
import com.example.binhdv35.fishing_sales_manage.model.Customer;
import com.example.binhdv35.fishing_sales_manage.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

//    private Button btnSignOut;
    private ImageView imgUser;
    private TextView tvAccountName, tvUserName, tvPhoneNumber, tvEmail, tvAdress;

    private MainActivity mainActivity;

    public static List<Customer> customerList;

    private ProgressDialog progressDialog;
    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //anh xa---
        initUI(view);
        settingPDialog();
        onClickV();
        getDataCustomer();
//        setTextData();
    }


    private void setTextData() {
        for (int i = 0; i < customerList.size() ; i++) {
            Account account = Account.accountList.get(i+1);
            Customer customer = customerList.get(i);
            if (customer.getId().equals(SignInActivity.ID_CUSTOMER)){
                   tvAccountName.setText("Tên tài khoản: "+account.getAccountName());
                   tvEmail.setText(customer.getEmail());
                   tvPhoneNumber.setText(customer.getPhoneNumber());
                   tvAdress.setText(customer.getAdress());
                   tvUserName.setText("Tên người dùng: "+customer.getName());
                   Picasso.get().load(customer.getImage())
                           .placeholder(R.drawable.ic_baseline_image_24)
                           .error(R.drawable.ic_baseline_error_24)
                           .into(imgUser);
            }
        }
    }


    private void getDataCustomer() {
        showPDialog();
        customerList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLJson.KEY_GET_CUSTOMER,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d("response", response.toString()); // dữ liệu trả về
                        try {
                            for (int i = 0; i < response.length() ; i++) {
                                JSONObject product = (JSONObject) response.get(i);
                                String id = product.getString("_id");
                                String name = product.getString("name");
                                String adress = product.getString("adress");
                                String phone_number = product.getString("phone_number");
                                String email = product.getString("email");
                                String image = product.getString("image");
                                Log.d("fffeeee", name);
                                customerList.add(new Customer(id, name, adress,phone_number,email,image));
                                Log.d("cusssslist---",customerList.size()+"");
                            }
                            setTextData();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        hidePDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error" + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                hidePDialog();
            }
        });

        RequestQueueController.getInstance().addToRequestQueue(jsonArrayRequest);
        hidePDialog();
    }

    private void initUI(View view) {
        imgUser = view.findViewById(R.id.account_image);
        tvAccountName = view.findViewById(R.id.account_tv_account_name);
        tvUserName = view.findViewById(R.id.account_tv_user_name);
        tvAdress = view.findViewById(R.id.account_tv_adress);
        tvEmail = view.findViewById(R.id.account_tv_email);
        tvPhoneNumber = view.findViewById(R.id.account_tv_phone_number);
    }
    private void onClickV() {
//        btnSignOut.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    //p dialog------
    private void settingPDialog() {
        progressDialog = new ProgressDialog(getContext());
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
}