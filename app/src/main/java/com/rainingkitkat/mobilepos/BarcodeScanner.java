package com.rainingkitkat.mobilepos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class BarcodeScanner extends AppCompatActivity {
    dbHandler db;
    dialogBox dialogBox;
    Utils utils;

    private SurfaceView barcodeSurfaceView;
    private TextView textResult;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private final int RequestCameraPermission = 1001;

    public static boolean isDialogOpen = false;
    private String barcode = "";
    public static String sendProductName;
    public static String sendProductPrice;

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        db = new dbHandler(this);
        dialogBox = new dialogBox();
        utils = new Utils();

        barcodeSurfaceView = findViewById(R.id.barcodeCamera);
        textResult = findViewById(R.id.textResult);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).setAutoFocusEnabled(true).build();
        barcodeSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(BarcodeScanner.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermission);
                    return;
                }
                try {
                    cameraSource.start(barcodeSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> code = detections.getDetectedItems();

                if (code.size() != 0){
                    textResult.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isDialogOpen == false){
                                Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(300);
                                textResult.setText(code.valueAt(0).displayValue);
                                sendProductName = db.getBarcode(textResult.getText().toString());
                                sendProductPrice = db.getPrice(textResult.getText().toString());
                                //dialogBox.setProductName(sendProductName);
                                //textResult.setText(barcode);
                                if(sendProductName.matches("Not A Valid Barcode")){
                                    Toast.makeText(BarcodeScanner.this, "Not A Valid Barcode", Toast.LENGTH_SHORT).show();
                                    isDialogOpen = false;
                                } else {
                                    username = utils.getUsername(BarcodeScanner.this);
                                    db.addItemToCart(sendProductName, sendProductPrice, username);
                                    openDialog();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermission: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    try {
                        cameraSource.start(barcodeSurfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void openDialog(){
        dialogBox dialogBox = new dialogBox();
        dialogBox.show(getSupportFragmentManager(),"dialogBox");
        isDialogOpen = true;
    }
}
