package com.example.binhdv35.fishing_sales_manage.Fragment;

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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.binhdv35.fishing_sales_manage.Activity.MainActivity;
import com.example.binhdv35.fishing_sales_manage.Activity.ProductDetailActivity;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.app.URLJson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.JarException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductManageFragment extends Fragment {

    private EditText edName, edPrice, edImage, edColor, edNote;
    private Button btnAdd, btnCancel;

    public ProductManageFragment() {
        // Required empty public constructor
    }


    public static ProductManageFragment newInstance() {
        ProductManageFragment fragment = new ProductManageFragment();
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
        return inflater.inflate(R.layout.fragment_product_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);

        btnCancel.setOnClickListener(v -> {
            clearEditText();
        });

        addNewProduct();
    }

    private void addNewProduct() {
        btnAdd.setOnClickListener(v -> {
            String name = edName.getText().toString().trim();
            String image = edImage.getText().toString().trim();
            String price  = edPrice.getText().toString().trim();
            String color = edColor.getText().toString().trim();
            String note = edNote.getText().toString().trim();

            JSONObject productJson = new JSONObject();
            try {
                productJson.put("name" , name);
                productJson.put("price" , price);
                productJson.put("image" , image);
                productJson.put("color", color);
                productJson.put("note", note);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLJson.KEY_POST_PRODUCT, productJson,
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

            if (validateEdt(name, image, price,color,note) == true){
                RequestQueueController.getInstance().addToRequestQueue(request);
                Toast.makeText(getContext(), "Add new successfully!", Toast.LENGTH_SHORT).show();
                clearEditText();
            }else {
                Toast.makeText(getContext(), "Không để trông các ô!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEdt(String name, String image, String price, String color, String note) {
        if(name.isEmpty() || image.isEmpty() || price.isEmpty() || color.isEmpty() || note.isEmpty())
            return false;
        return true;
    }

    private void clearEditText() {
        edName.setText("");
        edPrice.setText("");
        edImage.setText("");
        edColor.setText("");
        edNote.setText("");
    }

    private void initUi(View view) {
        edName = view.findViewById(R.id.manage_ed_name_product);
        edPrice = view.findViewById(R.id.manage_ed_price_product);
        edImage = view.findViewById(R.id.manage_ed_image_product);
        edColor = view.findViewById(R.id.manage_ed_color_product);
        edNote = view.findViewById(R.id.manage_ed_note_product);
        btnAdd = view.findViewById(R.id.manage_btn_add_product);
        btnCancel = view.findViewById(R.id.manage_btn_cancel_edit);
    }
}