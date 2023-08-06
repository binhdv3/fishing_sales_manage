package com.example.binhdv35.fishing_sales_manage.contacts;

import com.example.binhdv35.fishing_sales_manage.app.Utils;

public class URLJson {

    private String ipv4 = Utils.getIPAddress(true);
    private static final String HTTP = "http://10.0.2.2:3000/";

    public static final String KEY_PRODUCT = HTTP;
    public static final String KEY_POST_PRODUCT = HTTP + "product/create";
    public static final String KEY_DELETE_PRODUCT = HTTP + "product/delete/";
    public static final String KEY_PUT_PRODUCT = HTTP +"product/edit/";
    public static final String KEY_GET_ACCOUNT = HTTP +"account";
    public static final String KEY_POST_ACCOUNT = HTTP +"account/create";
    public static final String KEY_GET_CUSTOMER = HTTP +"customer";
    public static final String KEY_POST_CUSTOMER = HTTP +"customer/create";
    public static final String KEY_POST_ODER = HTTP +"oder/create";
    public static final String KEY_POST_CART = HTTP +"cart/create";
    public static final String KEY_GET_CART = HTTP +"cart";
    public static final String KEY_DELETE_CART = HTTP +"cart/delete/";
    public static final String KEY_PUT_CART = HTTP +"cart/edit/";
    public static final String KEY_GET_ODER = HTTP +"oder";

}
