package com.example.portefeuillealilo.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.portefeuillealilo.model.User;
import com.example.portefeuillealilo.ui.LoginActivity;


public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "my_shared_preff";
    private static final String KEY_USERNAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private static final String KEY_SOLDE = "solde";
    private static final String KEY_REVENU = "revenu";
    private static final String KEY_DEPENSES = "depenses";
    private static final String KEY_ID = "id";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASS, user.getPassword());
        editor.putString(KEY_SOLDE, user.getSolde());
        editor.putString(KEY_REVENU, user.getRevenu());
        editor.putString(KEY_DEPENSES, user.getDepenses());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, 0),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASS, null),
                sharedPreferences.getString(KEY_SOLDE, null),
                sharedPreferences.getString(KEY_DEPENSES, null),

                sharedPreferences.getString(KEY_REVENU, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}