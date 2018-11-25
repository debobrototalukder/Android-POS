package com.rainingkitkat.mobilepos;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    static SharedPreferences sp;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    public static void isLoggedIn(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean("logged", true);
        editor.commit();
    }

    public static void isNotLoggedIn(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean("logged", false);
        editor.commit();
    }

    public static boolean getLoggedIn(Context context){
        return getPrefs(context).getBoolean("logged", true);
    }

    public static void setUsername(Context context, String username){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("username", username);
        editor.commit();
    }

    public static String getUsername(Context context){
        return getPrefs(context).getString("username", "default_username");
    }

    public static int getQuantity(Context context){
        return getPrefs(context).getInt("quantity", 0);
    }

    public static void setQuantity(Context context, int quantity){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("quantity", quantity);
        editor.commit();
    }
}
