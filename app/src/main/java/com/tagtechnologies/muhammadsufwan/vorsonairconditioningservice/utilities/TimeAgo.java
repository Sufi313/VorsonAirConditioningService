package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities;


import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeAgo {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static String getFormatedTimeAgo(String timeStamp){

        if (timeStamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(timeStamp);
            } catch (ParseException e) {

                e.printStackTrace();
            }

            Log.d("Milliseconds" , String.valueOf(myDate.getTime()));

//            CharSequence newtime = DateUtils.getRelativeTimeSpanString(myDate.getTime());
//            final StringBuilder sb = new StringBuilder(newtime.length());
//            sb.append(newtime);
//            return sb.toString();

            return TimeAgo.getTimeAgo(myDate.getTime());

        }else{
            return "";
        }

    }

    public boolean appInstalledOrNot(String uri, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Check Other App",e.getMessage());
        }

        return false;
    }

}
