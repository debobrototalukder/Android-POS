package com.rainingkitkat.mobilepos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class dialogBox extends AppCompatDialogFragment {
    BarcodeScanner barcodeScanner = new BarcodeScanner();

    private TextView groceryName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_dialogbox, null);
        builder.setView(view);

        groceryName = view.findViewById(R.id.ItemName);
        groceryName.setText("Product Name : " + barcodeScanner.sendProductName
        + "\n\nProduct Price : " + barcodeScanner.sendProductPrice);


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
