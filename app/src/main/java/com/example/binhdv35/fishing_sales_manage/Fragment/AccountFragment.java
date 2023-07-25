package com.example.binhdv35.fishing_sales_manage.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.binhdv35.fishing_sales_manage.Activity.MainActivity;
import com.example.binhdv35.fishing_sales_manage.Activity.SignInUpActivity;
import com.example.binhdv35.fishing_sales_manage.R;

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
        onClickV();
        getDataCustomer();
    }

    private void getDataCustomer() {
        showPDialog();
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest();
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