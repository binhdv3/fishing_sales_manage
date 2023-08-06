package com.example.binhdv35.fishing_sales_manage.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.binhdv35.fishing_sales_manage.Activity.ProductDetailActivity;
import com.example.binhdv35.fishing_sales_manage.Activity.SignInActivity;
import com.example.binhdv35.fishing_sales_manage.Fragment.CartFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.HomeFragment;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.InterfaceGetDataCart;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Cart;
import com.example.binhdv35.fishing_sales_manage.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;



public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartViewholder>{

//    InterFaceCart interFaceCart;
    private Context context;
    private List<Cart> cartList;

    public void interCall( InterfaceGetDataCart interfaceGetDataCart ){
        this.interfaceGetDataCart = interfaceGetDataCart;
    }
    InterfaceGetDataCart interfaceGetDataCart;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent ,false);
        return new CartViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewholder holder, int position) {
        Cart cart = cartList.get(position);
        if(cart==null){
            return;
        }

        if (cart.getId_customer().equals(SignInActivity.ID_CUSTOMER)){
            for (int i = 0; i < HomeFragment.productList.size(); i++) {
                Product product = HomeFragment.productList.get(i);
                if (cart.getId_product().equals(product.getId())){
                    Picasso.get().load(product.getImage())
                            .placeholder(R.drawable.ic_baseline_image_24)
                            .error(R.drawable.ic_baseline_error_24)
                            .into(holder.imgProduct);
                    holder.tvName.setText(product.getName());
                    holder.tvPrice.setText(product.getPrice()+"");
                    holder.edSoluong.setText(cart.getQuantity()+"");
                    break;
                }
            }
        }
        //cộng trừ sản phầm----
        quantity(holder,position);
        //xoa san pham trong cart
        holder.lnItemCart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xoá");
                builder.setMessage("Bạn muốn xoá sản phẩm khỏi giỏ hàng?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = URLJson.KEY_DELETE_CART + cart.getId(); //url xoá
                        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,
                                        "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //xoá thành công-----------
                        RequestQueueController.getInstance().addToRequestQueue(request);
                        interfaceGetDataCart.loadDataCart();
                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // code something
                    }
                });
                builder.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cartList != null)
            return cartList.size();
        return 0;
    }

    public class CartViewholder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView tvName, tvPrice,btnMinus,btnPlus;
        private EditText edSoluong;
        private LinearLayout lnItemCart;

        public CartViewholder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.cart_img_product);
            tvName= itemView.findViewById(R.id.cart_tv_name);
            tvPrice= itemView.findViewById(R.id.cart_tv_price);
            btnMinus= itemView.findViewById(R.id.cart_btnMinus);
            btnPlus= itemView.findViewById(R.id.cart_btnPlus);
            edSoluong = itemView.findViewById(R.id.cart_ed_soluong);
            lnItemCart = itemView.findViewById(R.id.item_ln_cart);
        }
    }

    private void quantity(CartViewholder holder,int position) //cộng trừ số lượng---
    {
        Cart cart = cartList.get(position);
        //
        holder.btnMinus.setOnClickListener(v -> {
            interfaceGetDataCart.loadDataCart();
            if (cart.getQuantity() >= 2){
                JSONObject cartJson = new JSONObject();
                try {
                    cartJson.put("quantity",cart.getQuantity()-1);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                String url = URLJson.KEY_PUT_CART + cart.getId(); //url put -----------------

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, cartJson,
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
                RequestQueueController.getInstance().addToRequestQueue(request);
                holder.edSoluong.setText(cart.getQuantity()+"");
            }

        });

        holder.btnPlus.setOnClickListener(v -> {
            interfaceGetDataCart.loadDataCart();
            JSONObject cartJson = new JSONObject();
            try {
                cartJson.put("quantity", cart.getQuantity()+1);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            String url = URLJson.KEY_PUT_CART + cart.getId(); //url put -----------------

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, cartJson,
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
            RequestQueueController.getInstance().addToRequestQueue(request);

            holder.edSoluong.setText(cart.getQuantity()+"");
        });
    }
}
