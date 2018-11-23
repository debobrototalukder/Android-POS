package com.rainingkitkat.mobilepos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
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
        groceryName.setText(barcodeScanner.sendProductName);


        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        barcodeScanner.isDialogOpen = false;
    }

    //ToDo Finish The Dialog. Add The Plus Minus Button.
}
