package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.extrass;
/*******************************************************/
/* Created by muhammad.sufwan on 5/25/2018 user email ${EMAIL};. */

/*******************************************************/
import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
