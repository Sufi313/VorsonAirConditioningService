package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ConfirmPayAmountActivity extends AppCompatActivity {

    private TextView oneTxt, twoTxt, threeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay_amount);
        Toolbar toolbar = (Toolbar)findViewById(R.id.amountConfirmToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        oneTxt = (TextView) findViewById(R.id.textBuyone);
        twoTxt = (TextView) findViewById(R.id.textBuytwo);
        threeTxt = (TextView)findViewById(R.id.textBuythree);


        Intent intent = getIntent();
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("paymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("paymentAmount"));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String paymentAmount){
        try {
            oneTxt.setText(response.getString("id"));
            twoTxt.setText(response.getString(String.format("$",paymentAmount)));
            threeTxt.setText(response.getString("state"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
