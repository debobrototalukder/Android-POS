package com.rainingkitkat.mobilepos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    dbHandler db;
    Utils utils;

    private String username;
    private String balance;
    private double totalAmountDouble = 0;

    private TextView balanceTV;
    private TextView totalAmount;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        utils = new Utils();
        db = new dbHandler(this);

        balanceTV = findViewById(R.id.balance);
        totalAmount = findViewById(R.id.total_amount);
        recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        username = utils.getUsername(this);

        balance = db.getUserBalance(username);

        balanceTV.setText("Balance : " + balance + "Dhs");
        totalAmount.setText("0 Dhs");

        productList = new ArrayList<>();

        // Adds All The Products To The View
        for (int i = 0; i < db.getProductName().size(); i++){
            productList.add(new Product(db.getProductName().get(i), db.getProductPrice().get(i), db.getProductQuantity().get(i)));
        }

        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        //totalAmount.setText();

    }

    public void backBtn(View view){
        finish();
    }

    public void confirmPurchase(View view){
        //ToDo : This is supposed to take you to the home page
        finish();
    }

    //ToDo : Do This Based On User ID
}
