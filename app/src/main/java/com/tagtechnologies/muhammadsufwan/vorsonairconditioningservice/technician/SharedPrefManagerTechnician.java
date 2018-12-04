package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.LounchActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.Client;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TechnicianModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.LoginClientActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by muhammad.sufwan on 11/22/2017.
 */

public class SharedPrefManagerTechnician {

    private static final String SHARED_PREF_NAME = "vorsonTechnician";
    private static final String SHARED_PREF_IMAGE = "technicianImage";
    private static final String KEY_ID = "keyid";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DOB = "dateOfBirth";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_IMAGE = "image";


    private static SharedPrefManagerTechnician mInstance;
    private static Context mCtx;

    private SharedPrefManagerTechnician(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerTechnician getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerTechnician(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void setImage(String path) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_IMAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMAGE, path);
        editor.apply();
    }

    public boolean isImageIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_IMAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_IMAGE, null) != null;
    }

    public String getImage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_IMAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_IMAGE, null);
    }

    public void clearImage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_IMAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public void clientLogin(TechnicianModule technicianModule) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, technicianModule.getId());
        editor.putString(KEY_NAME, technicianModule.getName());
        editor.putString(KEY_EMAIL, technicianModule.getEmail());
        editor.putString(KEY_ADDRESS, technicianModule.getAddress());
        editor.putString(KEY_MOBILE, technicianModule.getMobile());
        editor.putString(KEY_DOB, technicianModule.getDob());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    //this method will give the logged in user
    public TechnicianModule getTechnician() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new TechnicianModule(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_MOBILE, null),
                sharedPreferences.getString(KEY_DOB, null)

        );
    }

    //this method will logout the Technician
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LounchActivity.class));
    }

    public void logoutWithNoIntent() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
