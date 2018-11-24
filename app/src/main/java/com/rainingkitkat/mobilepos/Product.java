package com.rainingkitkat.mobilepos;

import android.content.Context;

public class Product {
    private Context context;

    private String name;
    private String price;
    private String quantity;

    public Product(String name, String price, String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}
