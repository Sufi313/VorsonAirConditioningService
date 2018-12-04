package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.Client;

/**
 * Created by muhammad.sufwan on 11/22/2017.
 */

public class SharedPrefManagerClient {


    private static final String SHARED_PREF_NAME = "vorsonClient";
    private static final String SHARED_PREF_DEVICE_NAME = "deviceToken";
    private static final String KEY_ID = "keyid";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "number";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DEVICE_TOKEN = "token";

    private static SharedPrefManagerClient mInstance;
    private static Context mCtx;

    private SharedPrefManagerClient(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerClient(context);
        }
        return mInstance;
    }


    //method to let the user login
    //this method will store the user data in shared preferences
    public void clientLogin(Client client) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, client.getId());
        editor.putString(KEY_NAME, client.getName());
        editor.putString(KEY_EMAIL, client.getEmail());
        editor.putString(KEY_PHONE, client.getNumber());
        editor.putString(KEY_ADDRESS, client.getAddress());
        editor.putInt(KEY_STATUS, client.getStatus());

        editor.apply();
    }

    public void deviceToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_DEVICE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_DEVICE_TOKEN, token);

        editor.apply();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_DEVICE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DEVICE_TOKEN,null);
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    //this method will give the logged in user
    public Client getClient() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Client(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE,null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getInt(KEY_STATUS,-1)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginClientActivity.class));
    }

    public void logoutWithNoIntent() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
