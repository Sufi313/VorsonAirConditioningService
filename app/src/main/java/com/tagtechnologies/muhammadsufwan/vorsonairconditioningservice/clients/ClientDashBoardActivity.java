package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

//            /***********************(SUFI)******************/
//            /** Created by Muhammad Sufwan on 04/04/1991  **/
//            /**********************(TECHS)******************/

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.TicketActivity;

import java.util.Objects;

public class ClientDashBoardActivity extends AppCompatActivity {

    private LinearLayout paymentBox, profiletBox, ticketBox, notDecideBox,ticketList;
    private ImageView goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dash_board);

        Toolbar toolbar = findViewById(R.id.toolbarClientDashboard);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

//        BINDING LAUOUT FOR LISTNER
        goBack = findViewById(R.id.goBackClientDashboard);
        paymentBox = findViewById(R.id.dashBoardPaymentBox);
        profiletBox = findViewById(R.id.dashBoardProfileBox);
        ticketBox = findViewById(R.id.dashBoardTicketBox);
        notDecideBox = findViewById(R.id.dashBoardNotDecideBox);
        ticketList = findViewById(R.id.dashBoardTicketList);

        if (!SharedPrefManagerClient.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginClientActivity.class));
        }

//        logUser();
        
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        paymentBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientDashBoardActivity.this, PaymentListActivity.class);
                i.putExtra("get_type", "1");
                startActivity(i);
            }
        });

        profiletBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientDashBoardActivity.this, ProfileActivity.class));
            }
        });

        profiletBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInf = wifiMan.getConnectionInfo();
                int ipAddress = wifiInf.getIpAddress();
                String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
                Toast.makeText(ClientDashBoardActivity.this, "Your IP address is "+ip, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ticketBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientDashBoardActivity.this, TicketActivity.class));
            }
        });

        notDecideBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ClientDashBoardActivity.this, PaymentListActivity.class);
                i.putExtra("get_type", "0");
                startActivity(i);
            }
        });

        ticketList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ClientDashBoardActivity.this, ComplaintTicketListActivity.class);
                startActivity(i);
            }
        });

    }

}
