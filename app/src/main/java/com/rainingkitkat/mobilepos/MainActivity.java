package com.rainingkitkat.mobilepos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    dbHandler db;
    Validation validation;
    Utils utils;

    private Button signup;
    private EditText username;
    private EditText password;
    private TextView invalid;

    private boolean isFieldEmpty;
    private boolean verifyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.sign_up);
        username = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        invalid = findViewById(R.id.invalid);

        //db = new databaseHandler(this);
        db = new dbHandler(this);
        validation = new Validation();
        utils = new Utils();

        invalid.setVisibility(View.INVISIBLE);

        if(utils.getLoggedIn(this)){
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
    }



    public void SignUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void toHome(View view){
        isFieldEmpty = validation.isLoginFieldEmpty(username.getText().toString(), password.getText().toString());

        if(isFieldEmpty == true){
            invalid.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Fill All The Fields To Login", Toast.LENGTH_SHORT).show();
        } else {
            verifyUser = db.verifyCredentials(username.getText().toString(), password.getText().toString());

            if(verifyUser == true){
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                utils.isLoggedIn(this);
                utils.setUsername(this, username.getText().toString());
            } else {
                invalid.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //ToDo : Make The Home Page. Make It Better If You Can
    //TODO : Make The Cart
    //ToDo : Make The Firebase Database
    //ToDo : Change The Color Of The Buttons
    //ToDo : Make Admin Account
    //ToDo : Add Balance
}
