package com.example.binhdv35.fishing_sales_manage.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.adapter.ProductAdapter;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView rcv_product;
    private ProgressDialog progressDialog;

    public static List<Product> productList;
    private ProductAdapter productAdapter;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        settingPDialog();
        productList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        productAdapter = new ProductAdapter(productList,getContext());
        rcv_product.setLayoutManager(gridLayoutManager);
        rcv_product.setAdapter(productAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        productList.clear();
        makeProductRequest();
    }

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

    private void makeProductRequest() {
        showPDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLJson.KEY_PRODUCT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d("response", response.toString()); // dữ liệu trả về
                        try {
                            for (int i = 0; i < response.length() ; i++) {
                                JSONObject product = (JSONObject) response.get(i);
                                String id = product.getString("_id");
                                String name = product.getString("name");
                                int price = product.getInt("price");
                                String image = product.getString("image");
                                String color = product.getString("color");
                                String note = product.getString("note");

                                productList.add(new Product(id,name,image,color,note,price));
                            }

                            productAdapter = new ProductAdapter(productList,getContext());
                            rcv_product.setAdapter(productAdapter);

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
    }

    private void initUi(View view) {
        rcv_product = view.findViewById(R.id.home_rcv_product);
    }

}