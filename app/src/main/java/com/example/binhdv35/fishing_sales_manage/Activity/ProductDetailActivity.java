package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.binhdv35.fishing_sales_manage.Fragment.HomeFragment;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgProduct;
    private TextView tvNameProduct, tvPriceProduct, tvNoteProduct;
    private Button btnDelete, btnEdit, btnAddToCart,btnBuyNow;
    private int postion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initUi();
        getInfoProduct();
        btnDelete.setOnClickListener(v -> {  //xoá sản phẩm cho admin
            makeDeleteProduct();
        });
        btnEdit.setOnClickListener(v -> {  //sửa sản phẩm cho admin
            makeEditProduct();
        });
        btnAddToCart.setOnClickListener(v -> { //Thêm sản phẩm vào giỏ hàng
            addTocCart();
        });
        btnBuyNow.setOnClickListener(v -> {
            buyNow();
        });

        decentralization();//phân quyền
    }

    private void buyNow() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_oder);//set layout cho dialog
        EditText edQuantity = bottomSheetDialog.findViewById(R.id.oder_ed_quantity);
        Button btnOder = bottomSheetDialog.findViewById(R.id.oder_btn_oder);
        Button btnCancel = bottomSheetDialog.findViewById(R.id.oder_btn_cancel);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(date); // lâý ngày hiện tại

        btnOder.setOnClickListener(v -> {
            JSONObject oderJson = new JSONObject();
            try {
                oderJson.put("id_customer" , SignInActivity.ID_CUSTOMER);
                oderJson.put("id_product" , HomeFragment.productList.get(postion).getId());
                oderJson.put("oder_date" , currentDate);
                oderJson.put("total_amount" ,  Integer.parseInt(edQuantity.getText().toString().trim())
                        * HomeFragment.productList.get(postion).getPrice());
                oderJson.put("quantity", Integer.parseInt(edQuantity.getText().toString().trim()));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLJson.KEY_POST_ODER, oderJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int success = response.getInt("success");
                                String message = response.getString("message");
                                Toast.makeText(ProductDetailActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("json_error" , error.getMessage());
                        }
                    }
            );

            RequestQueueController.getInstance().addToRequestQueue(request);
        });


        btnCancel.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void addTocCart() {
        JSONObject cartJson = new JSONObject();
        try {
            cartJson.put("id_customer" , SignInActivity.ID_CUSTOMER);
            cartJson.put("id_product" , HomeFragment.productList.get(postion).getId());
            cartJson.put("quantity", 1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLJson.KEY_POST_CART, cartJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt("success");
                            String message = response.getString("message");
                            Toast.makeText(ProductDetailActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("json_error" , error.getMessage());
                    }
                }
        );

        RequestQueueController.getInstance().addToRequestQueue(request);
    }

    private void decentralization() {
        if (MainActivity.USER_NAME.equals("admin")){
            btnAddToCart.setVisibility(View.INVISIBLE);
            btnBuyNow.setVisibility(View.INVISIBLE);
        }else {
            btnBuyNow.setVisibility(View.VISIBLE);
            btnAddToCart.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    private void makeEditProduct() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_edit);//set layout cho dialog

        EditText edName = bottomSheetDialog.findViewById(R.id.edit_ed_name_product);
        EditText edPrice = bottomSheetDialog.findViewById(R.id.edit_ed_price_product);
        EditText edImage = bottomSheetDialog.findViewById(R.id.edit_ed_image_product);
        EditText edColor = bottomSheetDialog.findViewById(R.id.edit_ed_color_product);
        EditText edNote = bottomSheetDialog.findViewById(R.id.edit_ed_note_product);
        Button btnUpdate = bottomSheetDialog.findViewById(R.id.edit_btn_update_product);
        Button btnCancel = bottomSheetDialog.findViewById(R.id.edit_btn_cancle_edit);

        Product product = HomeFragment.productList.get(postion);
        edName.setText(product.getName());
        edPrice.setText(product.getPrice()+"");
        edImage.setText(product.getImage());
        edColor.setText(product.getColor());
        edNote.setText(product.getNote());

        btnUpdate.setOnClickListener(v -> {
            String name = edName.getText().toString().trim();
            String image = edImage.getText().toString().trim();
            String price  = edPrice.getText().toString().trim();
            String color = edColor.getText().toString().trim();
            String note = edNote.getText().toString().trim();

            //set dữ liệu thành json object
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

            String url = URLJson.KEY_PUT_PRODUCT + product.getId(); //url put -----------------

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, productJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Log.d("json_error" , error.getMessage());
                        }
                    }
            );
            //cập nhật thành công
            if (validateEdt(name, image, price,color,note) == true){
                RequestQueueController.getInstance().addToRequestQueue(request);
                Toast.makeText(ProductDetailActivity.this, "Update succseccfully!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Không để trông các ô!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private boolean validateEdt(String name, String image, String price, String color, String note) {
        if(name.isEmpty() || image.isEmpty() || price.isEmpty() || color.isEmpty() || note.isEmpty())
            return false;
        return true;
    }

    private void makeDeleteProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to delete this product?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //product/delete/:id
                String url = URLJson.KEY_DELETE_PRODUCT + HomeFragment.productList.get(postion).getId(); //url xoá
                StringRequest request = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetailActivity.this,
                                "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //xoá thành công-----------
                RequestQueueController.getInstance().addToRequestQueue(request);
                Toast.makeText(ProductDetailActivity.this, "Delete succseccfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductDetailActivity.this
                        , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    private void getInfoProduct() { //lấy thông qua postion từ ProductAdapter
        Intent intent = getIntent();
        if (intent != null) {
            postion = intent.getIntExtra("POSTION", 0);
            Product product = HomeFragment.productList.get(postion);
            Log.d("tag_id", product.getId());
            setDataUi(product.getImage(), product.getName(),
                    product.getPrice(),product.getColor(),product.getNote());
        }
    }

    private void setDataUi(String image, String name, int price, String color,String note) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        Picasso.get().load(image)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(imgProduct);
        tvNameProduct.setText(name);
        tvPriceProduct.setText("Giá: "+decimalFormat.format(price));
        tvNoteProduct.setText("Màu: "+color+"\n" + note);
    }

    private void initUi() {
        toolbar = findViewById(R.id.toolbar_productdetail);
        imgProduct = findViewById(R.id.detail_img_product);
        tvNameProduct = findViewById(R.id.detail_tv_name_product);
        tvPriceProduct = findViewById(R.id.detail_tv_price_product);
        tvNoteProduct = findViewById(R.id.detail_tv_note_product);
        btnDelete = findViewById(R.id.detail_btn_delete_product);
        btnEdit = findViewById(R.id.detail_btn_edit_product);
        btnAddToCart = findViewById(R.id.detail_btn_addtocart);
        btnBuyNow = findViewById(R.id.detail_btn_buynow);
    }
}