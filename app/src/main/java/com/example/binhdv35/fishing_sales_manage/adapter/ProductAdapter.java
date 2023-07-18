package com.example.binhdv35.fishing_sales_manage.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binhdv35.fishing_sales_manage.Activity.ProductDetailActivity;
import com.example.binhdv35.fishing_sales_manage.R;
import com.example.binhdv35.fishing_sales_manage.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewholderProduct> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewholderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewholderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderProduct holder, int position) {
        Product product = productList.get(position);
        if (product == null){
            return;
        }

        Log.d("imge", product.getImage());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");

        Picasso.get().load(product.getImage())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.imgProduct);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("Giá: "+decimalFormat.format(product.getPrice()));
        //set onclick
        holder.lnItemProduct.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("POSTION" , position);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (productList != null) return productList.size();
        return 0;
    }

    public class ViewholderProduct extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView tvName, tvPrice;
        private LinearLayout lnItemProduct;

        public ViewholderProduct(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.item_img_product);
            tvName = itemView.findViewById(R.id.item_tv_name_product);
            tvPrice = itemView.findViewById(R.id.item_tv_price_product);
            lnItemProduct = itemView.findViewById(R.id.item_ln_product);
        }
    }
}
