package com.example.binhdv35.fishing_sales_manage.model;

public class Cart {
    private String id, id_product, id_customer;
    private int quantity;

    public Cart(String id, String id_product, String id_customer, int quantity) {
        this.id = id;
        this.id_product = id_product;
        this.id_customer = id_customer;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }
    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }
}
