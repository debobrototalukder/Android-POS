package com.rainingkitkat.mobilepos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Cart extends AppCompatActivity {
    dbHandler db;
    Utils utils;

    private String username;
    private String balance;

    private TextView balanceTV;
    private TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        utils = new Utils();
        db = new dbHandler(this);

        balanceTV = findViewById(R.id.balance);
        totalAmount = findViewById(R.id.total_amount);

        username = utils.getUsername(this);

        balance = db.getUserBalance(username);

        balanceTV.setText("Balance : " + balance);
        totalAmount.setText("0");

    }

    public void backBtn(View view){
        finish();
    }

    public void confirmPurchase(View view){
        //ToDo : This is supposed to take you to the home page
        finish();
    }
}
