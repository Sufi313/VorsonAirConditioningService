package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician;


import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TicketSpinnerAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ViewAssignJobActivity extends AppCompatActivity {


    private int assigne_job_id;
    private int complaint_no;
    private TextView complaintNumber, name, phoneNumber, serviceType, servicePriority, tvMessage, tvAddress;
    private Spinner forChangedStatus;
    private List<String> statusList;
    private Button submitStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assign_job);
        Toolbar toolbar = findViewById(R.id.toolbarViewAssignJobActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ImageView goBack = findViewById(R.id.goBackViewAssignJobActivity);
       // complaintNumber = findViewById(R.id.tvComplainNumber_ViewAssignJobActivity);
        name = findViewById(R.id.tvClientName_ViewAssignJobActivity);
        phoneNumber = findViewById(R.id.tvClientPhone_ViewAssignJobActivity);
        serviceType = findViewById(R.id.tvServiceType_ViewAssignJobActivity);
        servicePriority = findViewById(R.id.tvServicePriority_ViewAssignJobActivity);
        tvMessage = findViewById(R.id.tvMessage_ViewAssignJobActivity);
        tvAddress = findViewById(R.id.tvAddress_ViewAssignJobActivity);
        forChangedStatus = findViewById(R.id.spnrStstus_ViewAssignJobActivity);
        submitStatus = findViewById(R.id.btnSubmit_ViewAssignJobActivity);
        submitStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changed();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        statusList = new ArrayList<>();
        statusList = Arrays.asList(getResources().getStringArray(R.array.status_list_for_accept_job));
        forChangedStatus.setAdapter(setSpinner(ViewAssignJobActivity.this, statusList));

        Intent intent = getIntent();
        assigne_job_id = intent.getIntExtra("id", 0);
        complaint_no = intent.getIntExtra("complain_id", 0);
        String client_name = intent.getStringExtra("client_name");
        String service_type = intent.getStringExtra("service_type");
        String service_riority = intent.getStringExtra("service_priority");
        String phone_number = intent.getStringExtra("phone_number");
        String message = intent.getStringExtra("message");
        String address = intent.getStringExtra("address");
//        String status = intent.getStringExtra("status");

        name.setText(client_name);
        serviceType.setText(service_type);
        servicePriority.setText(service_riority);
        phoneNumber.setText(phone_number);
        tvAddress.setText(address);
        tvMessage.setText(message);
       // complaintNumber.setText(String.valueOf(complaint_no));

    }

    @NonNull
    private TicketSpinnerAdapter setSpinner(Context context, List<String> items) {
        return new TicketSpinnerAdapter(context, items);
    }

    public void changed() {

        final String statusValue = forChangedStatus.getSelectedItem().toString();
        if (statusValue.equals("Select One")){
            Toast.makeText(this, "Please Select Drop Down Status", Toast.LENGTH_SHORT).show();
            return;
        }

        class ChangedStatus extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        if (obj.getString("result").equals("success")){
                            finish();
                            startActivity(new Intent(getApplicationContext(),CreateJobFormActivity.class));
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(assigne_job_id));
                params.put("status",statusValue);
                params.put("technician_id", String.valueOf(SharedPrefManagerTechnician.getInstance(ViewAssignJobActivity.this).getTechnician().getId()));
                //returing the response
                return requestHandler.sendPostRequest(Apis.SET_STATUS_URL, params);
            }
        }

        ChangedStatus ul = new ChangedStatus();
        ul.execute();

    }
}



