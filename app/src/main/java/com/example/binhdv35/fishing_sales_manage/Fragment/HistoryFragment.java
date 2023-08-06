package com.example.binhdv35.fishing_sales_manage.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.binhdv35.fishing_sales_manage.Activity.SignInActivity;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.adapter.OderAdapter;
import com.example.binhdv35.fishing_sales_manage.adapter.ProductAdapter;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Account;
import com.example.binhdv35.fishing_sales_manage.model.Customer;
import com.example.binhdv35.fishing_sales_manage.model.Oder;
import com.example.binhdv35.fishing_sales_manage.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView rcvOder;
    private OderAdapter oderAdapter;
    private List<Oder> oderList;
    private List<Customer> customerList;
    private ProgressDialog progressDialog;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvOder = view.findViewById(R.id.rcv_history);
        oderList = new ArrayList<>();
        customerList = new ArrayList<>();
        oderAdapter = new OderAdapter(oderList,customerList,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvOder.setLayoutManager(manager);
        settingPDialog();
        getDataOder();
        getDataCus();
    }

    private void getDataCus() {
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
                                customerList.add(new Customer(id, name, adress,phone_number,email,image));
                            }
                        }catch (Exception e){
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    private void getDataOder() {
        showPDialog();
        oderList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLJson.KEY_GET_ODER,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d("response", response.toString()); // dữ liệu trả về
                        try {
                            for (int i = 0; i < response.length() ; i++) {
                                JSONObject oder = (JSONObject) response.get(i);
                                String id = oder.getString("_id");
                                String id_customer = oder.getString("id_customer");
                                String id_product = oder.getString("id_product");
                                int total_amount = oder.getInt("total_amount");
                                int quantity = oder.getInt("quantity");
                                String oder_date= oder.getString("oder_date");

                                if (id_customer.equals(SignInActivity.ID_CUSTOMER)
                                        || SignInActivity.USERNAME_.equals("admin")) {
                                    oderList.add(new Oder(id, id_customer, oder_date, id_product, total_amount, quantity));
                                }
                            }

                            oderAdapter = new OderAdapter(oderList,customerList,getContext());
                            rcvOder.setAdapter(oderAdapter);

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
}