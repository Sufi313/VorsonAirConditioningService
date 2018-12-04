package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities;
/*******************************************************/
/* Created by muhammad.sufwan on 5/29/2018 user email ${EMAIL};. */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*******************************************************/
public class InternetCheck {

    private Context context;

    public InternetCheck(Context context) {
        this.context = context;
    }

    public boolean InternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager
                    .getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }else {
            return false;
        }
    }
}
