package com.rainingkitkat.mobilepos;

import android.util.Log;

public class Validation {
    private boolean isFullNameEmpty;
    private boolean isUsernameEmpty;
    private boolean isPasswordEmpty;

    public boolean isFieldEmpty(String fullName, String username, String password){
        if(fullName.matches("")){
            isFullNameEmpty = true;
        } else {
            isFullNameEmpty = false;
        }

        if(username.matches("")){
            isUsernameEmpty = true;
        } else {
            isUsernameEmpty = false;
        }

        if(password.matches("")){
            isPasswordEmpty = true;
        } else {
            isPasswordEmpty = false;
        }

        Log.d("Validation", "Name : " + isFullNameEmpty);
        Log.d("Validation", "Username : " + isUsernameEmpty);
        Log.d("Validation", "Password : " + isPasswordEmpty);

        if(isFullNameEmpty == true || isUsernameEmpty == true || isPasswordEmpty == true){
            return true;
        } else {
            return false;
        }
    }
}
