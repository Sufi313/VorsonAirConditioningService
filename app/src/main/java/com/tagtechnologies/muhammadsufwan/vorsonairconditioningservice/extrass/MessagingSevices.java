package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.extrass;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.SharedPrefManagerClient;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.TechnicianDashboardActivity;

public class MessagingSevices extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, s);

        SharedPrefManagerClient.getInstance(getApplicationContext()).deviceToken(s);
        System.out.println("My Token");
        System.out.println(s);
        
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,"FROM "+remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0){
            Log.d(TAG,"Message Data "+remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG,"Message Body:"+remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(String body){

        Intent intent = new Intent(this,TechnicianDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentText(body);
        notificationBuilder.setSound(notificationSound);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.holygrailz_uk_profile_ui);
        notificationBuilder.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());

    }

}
