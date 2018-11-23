package com.rainingkitkat.mobilepos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private dbHandler db;
    private Validation validation;

    private Button User_signup_button;
    private EditText fullName;
    private EditText username;
    private EditText password;
    private TextView usernameTF;

    private boolean validateUsername;
    private boolean isFieldEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        User_signup_button = findViewById(R.id.SignUp_user);
        fullName = findViewById(R.id.SignUp_fullname);
        username = findViewById(R.id.SignUp_username);
        password = findViewById(R.id.SignUp_password);
        usernameTF = findViewById(R.id.unique_username);
        usernameTF.setVisibility(View.INVISIBLE);

        db = new dbHandler(this);
        validation = new Validation();

        User_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFieldEmpty = validation.isFieldEmpty(fullName.getText().toString(), username.getText().toString(), password.getText().toString());
                Log.d("SignUp", "Are Fields Empty? " + isFieldEmpty + "");

                if(isFieldEmpty == true){
                    Toast.makeText(SignUp.this, "Fill All The Fields To Sign Up", Toast.LENGTH_SHORT).show();
                } else {
                    validateUsername = db.checkUsername(username.getText().toString().toLowerCase());

                    if(validateUsername == false){
                        usernameTF.setVisibility(View.VISIBLE);
                        Toast.makeText(SignUp.this, "Username Already Exists.\n Enter A New One", Toast.LENGTH_SHORT).show();
                    } else {
                        db.SignUpAddData(fullName.getText().toString(), username.getText().toString(), password.getText().toString());
                        finish();
                    }
                }
            }
        });
    }
}