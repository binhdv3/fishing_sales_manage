package com.example.binhdv35.fishing_sales_manage.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.binhdv35.fishing_sales_manage.Activity.SignInActivity;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.adapter.CartAdapter;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.InterfaceGetDataCart;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Cart;
import com.example.binhdv35.fishing_sales_manage.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements InterfaceGetDataCart {

    private RecyclerView rcvProduct;
    private TextView tvTongtien;
    private Button btnThanhToan;
    private CartAdapter cartAdapter;
    private ProgressDialog progressDialog;

    private List<Cart> cartList;

    public CartFragment() {
        // Required empty public constructor
    }


    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        cartList = new ArrayList<>();
        settingPDialog();
        cartAdapter = new CartAdapter(getContext(),cartList);
        cartAdapter.interCall(CartFragment.this::loadDataCart);
        LinearLayoutManager managercart = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvProduct.setLayoutManager(managercart);
        rcvProduct.setAdapter(cartAdapter);
        getDataCart();
    }
    private static int total = 0;
    private int toltalAmount(int i, String id_product){
        Cart cart = cartList.get(i);
        if (cart.getId_customer().equals(SignInActivity.ID_CUSTOMER)){
            for (int j = 0; j < HomeFragment.productList.size(); j++) {
                Product product = HomeFragment.productList.get(j);
                if (id_product.equals(product.getId())){
                    total += cart.getQuantity() * product.getPrice();
                }
            }
        }
        return total;
    }

    public void getDataCart() {
        cartList.clear(); //làm mới list khi vào giỏ hàng
        total = 0;
        showPDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLJson.KEY_GET_CART,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length() ; i++) {
                                JSONObject cart = (JSONObject) response.get(i);
                                String id = cart.getString("_id");
                                String id_product = cart.getString("id_product");
                                String id_customer = cart.getString("id_customer");
                                int quantity = cart.getInt("quantity");
                                if (id_customer.equals(SignInActivity.ID_CUSTOMER)){
                                    //sản phẩm có id_cus bằng vớitài khoản thì thêm vào list
                                    Log.d("idd_cus", id_customer+"----"+SignInActivity.ID_CUSTOMER);
                                    cartList.add(new Cart(id,id_product,id_customer,quantity));
                                    tvTongtien.setText(toltalAmount(i,id_product)+"");
                                }

                            }
                            cartAdapter = new CartAdapter(getContext(),cartList);
                            cartAdapter.interCall(CartFragment.this::loadDataCart);
                            rcvProduct.setAdapter(cartAdapter);
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

    private void initUi(View view) {
        rcvProduct = view.findViewById(R.id.cart_rcv_product);
        tvTongtien = view.findViewById(R.id.cart_tv_tongtienthanhtoan);
        btnThanhToan = view.findViewById(R.id.cart_btn_thanhtoan);
    }

    @Override
    public void loadDataCart() {
        getDataCart();
    }
}