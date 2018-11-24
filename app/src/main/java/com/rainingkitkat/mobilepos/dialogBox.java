package com.rainingkitkat.mobilepos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class dialogBox extends AppCompatDialogFragment {
    BarcodeScanner barcodeScanner;

    private TextView groceryName;
    private Button showCartBtn;
    private Button continueShoppingBtn;

    public static String sendProductName;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_dialogbox, null);
        builder.setView(view);

        barcodeScanner = new BarcodeScanner();

        groceryName = view.findViewById(R.id.ItemName);
        groceryName.setText("Product Name : " + barcodeScanner.sendProductName
        + "\n\nProduct Price : " + barcodeScanner.sendProductPrice + "\n\nThis Item Has Been Added To Your Cart");

        showCartBtn = view.findViewById(R.id.showCartBtn);
        continueShoppingBtn = view.findViewById(R.id.continueShoppingBtn);

        showCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Cart.class);
                startActivity(intent);
                //dismiss();
            }
        });

        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        barcodeScanner.isDialogOpen = false;
    }

    //ToDo : The Buttons Should Do Stuff
    //ToDo : Design It So It Fits The Screen
}
