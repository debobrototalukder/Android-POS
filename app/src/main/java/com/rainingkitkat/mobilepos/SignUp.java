package com.rainingkitkat.mobilepos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    private Button User_signup_button;
    private dbHandler db;
    private EditText fullName;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        User_signup_button = findViewById(R.id.SignUp_user);
        fullName = findViewById(R.id.SignUp_fullname);
        username = findViewById(R.id.SignUp_username);
        password = findViewById(R.id.SignUp_password);

        db = new dbHandler(this);

        User_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.SignUpaddData(fullName.getText().toString(), username.getText().toString(), password.getText().toString());
                Log.d("SignUp", fullName.getText().toString());
                Log.d("SignUp", username.getText().toString());
                Log.d("SignUp", password.getText().toString());
            }
        });
    }
}
//ToDo add validation to user signup this is soo cool