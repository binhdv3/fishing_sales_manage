package com.example.binhdv35.fishing_sales_manage.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.binhdv35.fishing_sales_manage.Fragment.AccountFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.CartFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.ChatFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.DiscountFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.HistoryFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.HomeFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.InvoiceFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.ProductManageFragment;
import com.example.binhdv35.fishing_sales_manage.Fragment.StatisticalFragment;
import com.example.binhdv35.fishing_sales_manage.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initID();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                0,0
                );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        decentralization(); //phân quyền cho admin,user...
        replaceFragment(HomeFragment.newInstance());// chuyển giao diện qua màn hình home

    }
    //----Phân quyền-----------------------
    private void decentralization() {
        intent = getIntent();
        String USER_NAME = intent.getStringExtra("USER_NAME");
        if(USER_NAME !=null)
        {
            if (USER_NAME.equalsIgnoreCase("admin"))
            {
                setVisibleFromID(R.id.id_item_product_management,true);//quan ly san pham-admin
                setVisibleFromID(R.id.id_item_statistical,true);// thong ke-admin
                setVisibleFromID(R.id.id_item_invoice,true);// hoa don-admin
            }else {
                setVisibleFromID(R.id.id_item_product_management,false);//quan ly san pham-admin
                setVisibleFromID(R.id.id_item_statistical,false);// thong ke-admin
                setVisibleFromID(R.id.id_item_invoice,false);// hoa don-admin
            }
        }
    }

    private void setVisibleFromID(int id_item, boolean tf){
        //true = hien || false = an
        navigationView.getMenu().findItem(id_item).setVisible(tf);
    }
    //--------------------------------------

    private void initID() {
        navigationView = findViewById(R.id.id_navigationView);
        toolbar = findViewById(R.id.id_toolBar);
        drawerLayout = findViewById(R.id.id_drawerLayout);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (drawerLayout.isDrawerOpen(navigationView))//opening nav
            {
                drawerLayout.closeDrawer(navigationView); // close nav
            }
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_item_home){ // màn hình chính
            replaceFragment(HomeFragment.newInstance());
        } else if (id == R.id.id_item_cart) { // giỏ hàng
            replaceFragment(CartFragment.newInstance());
        } else if (id == R.id.id_item_purchase_history) { // lịch sử mua hàng
            replaceFragment(HistoryFragment.newInstance());
        }else if (id == R.id.id_item_chat) { // tin nhắn
            replaceFragment(ChatFragment.newInstance());
        }else if (id == R.id.id_item_discount_codes) { // mã giảm giá
            replaceFragment(DiscountFragment.newInstance());
        }else if (id == R.id.id_item_product_management) { // quản lý sản phẩm
            replaceFragment(ProductManageFragment.newInstance());
        }else if (id == R.id.id_item_statistical) { //thống kê
            replaceFragment(StatisticalFragment.newInstance());
        }else if (id == R.id.id_item_invoice) { //thống kê
            replaceFragment(StatisticalFragment.newInstance());
        }else { //Hoá đơn
            replaceFragment(AccountFragment.newInstance());
        }

        drawerLayout.closeDrawer(navigationView);
        return false;
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.id_frameLayout,fragment);
        fragmentTransaction.commit();
    }
}