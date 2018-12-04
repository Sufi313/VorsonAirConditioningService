package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TicketSpinnerAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.ClientDashBoardActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.LoginClientActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.SharedPrefManagerClient;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class TicketActivity extends AppCompatActivity implements GetData.GetDataListener {

    private GpsTracker gpsTracker;
    private EditText etFullName, etEmail, etPhone, etMessage, etAddress;
    private Spinner help;
    private Button submit;
    private ImageView goBackImage;
    private RelativeLayout v;
    private Context context;
    private List<String> helpItems;
    private String addressData;
    private AlertDisplayer alertDisplayer;
    private String clientID = "0";
    private android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Toolbar toolbar = findViewById(R.id.toolbarTicketActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ImageView checkImage = findViewById(R.id.toolbarLogoImageTicketActivity);
        alertDialog = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(TicketActivity.this).build();

        checkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(TicketActivity.this, new String[]
                                {android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getLocation();
                etAddress.setText(addressData);
            }
        });

        etFullName = findViewById(R.id.ticketFullName);
        etEmail = findViewById(R.id.ticketEmail);
        etPhone = findViewById(R.id.ticketPhone);
        etAddress = findViewById(R.id.ticketAddress);
        etMessage = findViewById(R.id.ticketMessage);
        help = findViewById(R.id.spinner_help);
        goBackImage = findViewById(R.id.goBackHome);
        submit = findViewById(R.id.submitBtnTicket);
        v = findViewById(R.id.ticketLayout);
        context = this;

        String dateString = "2018-09-13 14:33:15";
        String timeAgo = TimeAgo.getFormatedTimeAgo(dateString);
        etMessage.setText(timeAgo);

        if (!SharedPrefManagerClient.getInstance(this).isLoggedIn()) {
            Snackbar.make(v, "If you are client please login", Snackbar.LENGTH_LONG)
                    .setAction("Login", new MyUndoListener())
                    .setActionTextColor(Color.WHITE)
                    .show();
        }

        if (SharedPrefManagerClient.getInstance(this).isLoggedIn()) {
            clientID = String.valueOf(SharedPrefManagerClient.getInstance(context).getClient().getId());
            etFullName.setText(SharedPrefManagerClient.getInstance(context).getClient().getName());
            etEmail.setText(SharedPrefManagerClient.getInstance(context).getClient().getEmail());
            etPhone.setText(SharedPrefManagerClient.getInstance(context).getClient().getNumber());
        }

        helpItems = new ArrayList<>();
        HashMap<String,String> params = new HashMap<>();
        params.put("key","android");
        new GetData(Apis.URL_GET_HELP_LIST,params,1,this).executeOnExecutor(THREAD_POOL_EXECUTOR);

        goBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubmitTicket();

            }
        });

    }

    private void SubmitTicket() {

        //first getting the values
        final String fullName = etFullName.getText().toString();
        final String email = etEmail.getText().toString();
        final String phone = etPhone.getText().toString();
        final String helpSpin = help.getSelectedItem().toString();
        final String message = etMessage.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Please enter your name");
            etFullName.requestFocus();
            return;
        }
        if (helpSpin.equalsIgnoreCase("Select One")) {
            alertDisplayer = new AlertDisplayer("Note", " Please Select Help", TicketActivity.this);
            alertDisplayer.alertDisplayer2();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Please enter your number");
            etPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(message)) {
            etMessage.setError("Enter message at least some words");
            etMessage.requestFocus();
            return;
        }
        //if everything is fine

        class Submit extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                alertDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                alertDialog.cancel();


                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        //getting the user from the response
                        JSONObject clientJson = obj.getJSONObject("tickets");

                        final AlertDialog.Builder myDialog = new AlertDialog.Builder(TicketActivity.this);
                        View myView = getLayoutInflater().inflate(R.layout.show_ticket_submit, null);
                        myDialog.setView(myView);

                        TextView adName = myView.findViewById(R.id.name_ticket_submit);
                        TextView adId = myView.findViewById(R.id.id_ticket_submit);
                        TextView adStatus = myView.findViewById(R.id.status_ticket_submit);
                        TextView adEmail = myView.findViewById(R.id.email_ticket_submit);
                        TextView adPhone = myView.findViewById(R.id.phone_ticket_submit);
                        TextView adHelp = myView.findViewById(R.id.help_ticket_submit);
                        TextView adMessage = myView.findViewById(R.id.message_ticket_submit);
                        Button adGoToDashBoard = myView.findViewById(R.id.go_dashboard);
                        Button adNewSubmit = myView.findViewById(R.id.new_ticket);

                        adName.setText("Dear : " + clientJson.getString("name"));
                        adId.setText( clientJson.getString("ticket_no"));
                        adEmail.setText(clientJson.getString("email"));
                        adPhone.setText(clientJson.getString("phone"));
                        adHelp.setText(clientJson.getString("help"));
                        adMessage.setText(clientJson.getString("message"));
                        if (clientJson.getInt("status") == 1) {
                            adStatus.setText("Pending");
                        }
                        if (clientJson.getInt("status") == 2) {
                            adStatus.setText("In Progress");
                        }
                        if (clientJson.getInt("status") == 3) {
                            adStatus.setText("Completed");
                        }
                        if (clientJson.getInt("status") == 4) {
                            adStatus.setText("Cancel");
                        }

                        adGoToDashBoard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (SharedPrefManagerClient.getInstance(getApplicationContext()).isLoggedIn()) {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), ClientDashBoardActivity.class));
                                }
                                Toast.makeText(TicketActivity.this, "Please login first", Toast.LENGTH_SHORT).show();
                            }
                        });
                        adNewSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), TicketActivity.class));
                            }
                        });

                        AlertDialog dialog = myDialog.create();
                        dialog.setCancelable(false);
                        dialog.show();


                    } else {
                        alertDisplayer = new AlertDisplayer("Note", obj.getString("message"), TicketActivity.this);
                        alertDisplayer.alertDisplayer2();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR TICKET SUBMIT", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("client_id", clientID);
                params.put("name", fullName);
                params.put("email", email);
                params.put("phone", phone);
                if (!etAddress.getText().toString().isEmpty()){ params.put("address", etAddress.getText().toString()); }
                params.put("help", helpSpin);
                params.put("message", message);

                return requestHandler.sendPostRequest(Apis.URL_TICKET, params);
                //returing the response
            }
        }

        Submit s = new Submit();
        s.execute();
    }

    @Override
    public void getDownloadData(String result, int request) {
        try {
            JSONObject obj = new JSONObject(result);
            if (!obj.getBoolean("error")){
                JSONArray array = obj.getJSONArray("list");
                for (int i = 0; i < array.length();i++){
                    JSONObject size = array.getJSONObject(i);
                    helpItems.add(size.getString("item"));
                }
            }
            else {
                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
            }

            TicketSpinnerAdapter ticketSpinnerAdapter = new TicketSpinnerAdapter(context, helpItems);
            help.setAdapter(ticketSpinnerAdapter);

        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            startActivity(new Intent(TicketActivity.this, LoginClientActivity.class));
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", strReturnedAddress.toString());
            } else {
                Toast.makeText(TicketActivity.this, "No Address Found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(TicketActivity.this, "No Address Found--->" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return strAdd;
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(TicketActivity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            addressData = getCompleteAddressString(latitude, longitude);

        } else {
            gpsTracker.showSettingsAlert();
        }
    }
}
