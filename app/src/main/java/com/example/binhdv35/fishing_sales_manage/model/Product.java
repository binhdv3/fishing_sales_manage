package com.example.binhdv35.fishing_sales_manage.model;

public class Product {
    private String id,name, image, color, note;
    private int price;

    public Product(String name, String image, String color, String note, int price) {
        this.name = name;
        this.image = image;
        this.color = color;
        this.note = note;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product(String id, String name, String image, String color, String note, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.color = color;
        this.note = note;
        this.price = price;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
