package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities;
/*******************************************************/
/* Created by muhammad.sufwan on 5/29/2018 user email ${EMAIL};. */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

/*******************************************************/
public class AlertDisplayer {
    private String title;
    private String message;
    private Context context;

    public AlertDisplayer(String title, String message, Context context) {
        this.title = title;
        this.message = message;
        this.context = context;
    }

   public void alertDisplayer() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OPEN SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog ok = builder.create();
        ok.show();
    }

   public void alertDisplayer2() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog ok = builder.create();
        ok.show();
    }
}
