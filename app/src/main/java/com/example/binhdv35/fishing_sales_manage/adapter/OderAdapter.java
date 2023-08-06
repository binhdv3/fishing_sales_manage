package com.example.binhdv35.fishing_sales_manage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.binhdv35.fishing_sales_manage.Activity.SignInActivity;
import com.example.binhdv35.fishing_sales_manage.Fragment.AccountFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.HomeFragment;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.app.RequestQueueController;
import com.example.binhdv35.fishing_sales_manage.contacts.URLJson;
import com.example.binhdv35.fishing_sales_manage.model.Account;
import com.example.binhdv35.fishing_sales_manage.model.Customer;
import com.example.binhdv35.fishing_sales_manage.model.Oder;
import com.example.binhdv35.fishing_sales_manage.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OderAdapter extends RecyclerView.Adapter<OderAdapter.OderViewholder> {

    private List<Oder> oderList;

    private Context context;

    private List<Customer> customerList;

    public OderAdapter(List<Oder> oderList,List<Customer> customerList, Context context) {
        this.oderList = oderList;
        this.customerList=customerList;
        this.context = context;
    }

    @NonNull
    @Override
    public OderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_oder, parent ,false);
        return new OderViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OderViewholder holder, int position) {
        Oder oder = oderList.get(position);
//        Customer customer = customerList.get(position);
        if(oder == null){
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");

            for (int i = 0; i < HomeFragment.productList.size(); i++) {
                Product product = HomeFragment.productList.get(i);
                if (oder.getIdProduct().equals(product.getId())){
                    Picasso.get().load(product.getImage())
                            .placeholder(R.drawable.ic_baseline_image_24)
                            .error(R.drawable.ic_baseline_error_24)
                            .into(holder.imgProduct);
                    holder.tvName.setText(product.getName());
                    holder.tvPrice.setText("Tổng đơn hàng: "+decimalFormat.format(oder.getTotal_amount()));
                    holder.tvQuantity.setText("số lượng: "+oder.getQuantity());
                    holder.tvDate.setText(oder.getDate());
                    if (SignInActivity.USERNAME_.equals("admin")){
                        holder.tvNameCus.setVisibility(View.VISIBLE);
//                        holder.tvNameCus.setText("Người dùng: "+customer.getName());
                    }else {
                        holder.tvNameCus.setVisibility(View.INVISIBLE);
                    }
                    break;
                }
            }
    }

    @Override
    public int getItemCount() {
        if (oderList != null)
            return oderList.size();
        return 0;
    }

    public class OderViewholder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView tvName, tvPrice,tvQuantity,tvDate,tvNameCus;

        public OderViewholder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.his_img_product);
            tvName= itemView.findViewById(R.id.oder_tv_name);
            tvPrice= itemView.findViewById(R.id.oder_tv_price);
            tvQuantity= itemView.findViewById(R.id.oder_tv_quantity);
            tvDate= itemView.findViewById(R.id.oder_tv_date);
            tvNameCus = itemView.findViewById(R.id.oder_tv_name_cus);
        }
    }

}
