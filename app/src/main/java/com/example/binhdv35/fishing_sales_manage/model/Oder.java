package com.example.binhdv35.fishing_sales_manage.model;

import java.util.Date;

public class Oder {
    private String id, idCustomer, date, idProduct;
    private int total_amount, quantity; //tổng tiền của đơn đăt hàng

    public Oder(String id, String idCustomer, String date, String idProduct, int total_amount, int quantity) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.date = date;
        this.idProduct = idProduct;
        this.total_amount = total_amount;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
