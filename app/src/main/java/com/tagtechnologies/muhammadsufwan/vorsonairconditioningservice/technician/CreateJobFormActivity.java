package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.CreateJobCustomSpinnerAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TicketSpinnerAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.AlertDisplayer;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.TimeAgo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class CreateJobFormActivity extends AppCompatActivity {

    private View myView;
    private static final String TAG = CreateJobFormActivity.class.getSimpleName();
    private NestedScrollView nestedScrollView;
    private JSONArray jsonArray;
    private AlertDialog dialog;
    private android.app.AlertDialog alertDialog;
    private Spinner spinner_scope_service1,
            spinner_scope_service2,
            spinner_scope_service3,
            spinner_scope_service4,
            spinner_scope_service5,
            spinner_scope_service6,
            spinner_scope_service7,
            spinner_scope_service8,
            spinner_scope_service9,
            spinner_refregerant_suction,
            spinner_system_leakage,
            spinner_idu_evaporator_coil,
            spinner_odu_condencer_coil,
            spinner_piping_installation_connectors,
            spinner_drain_system,
            spinner_power_control_system,
            spinner_idu_odu_system;
    private Button addNewPage, submitJob;

    private EditText ac_no, ac_location, generalComment;
    private ImageView goBack;

    List<String> items1;
    List<String> items2;
    List<String> items3;
    List<String> items4;
    List<String> items5;
    List<String> items6;
    List<String> items7;
    List<String> items8;
    List<String> items9;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_form);

        Toolbar toolbar = findViewById(R.id.toolbarCreateJobOrder);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        goBack = findViewById(R.id.goBackCreateJobOrder);
        nestedScrollView = findViewById(R.id.createJobNestedScrollView);

        alertDialog = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(CreateJobFormActivity.this).build();

        ImageView testImage = findViewById(R.id.createjobTestImage);

        testImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                TimeAgo timeAgo = new TimeAgo();
                boolean cBoll = timeAgo.appInstalledOrNot("com.whatsapp", CreateJobFormActivity.this);
                if (cBoll) {
                    sendIntent.setPackage("com.whatsapp");
                } else {
                    sendIntent.setAction(Intent.ACTION_SEND);
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my test text to send From Vorson.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mContext = this;
        jsonArray = new JSONArray();
        for (int k = 0; k < jsonArray.length(); k++) {
            jsonArray.remove(k);
        }

        ac_no = findViewById(R.id.create_job_et_ac_no);
        ac_location = findViewById(R.id.create_job_et_ac_location);
        generalComment = findViewById(R.id.generalComment_createJobFormActivity);

        spinner_scope_service1 = findViewById(R.id.spinner_createJob_idu_filters);
        spinner_scope_service2 = findViewById(R.id.spinner_createJob_idu_evaporator_coil);
        spinner_scope_service3 = findViewById(R.id.spinner_createJob_idu_blower_assambly);
        spinner_scope_service4 = findViewById(R.id.spinner_createJob_odu_condencer_coil);
        spinner_scope_service5 = findViewById(R.id.spinner_createJob_fan_motor);
        spinner_scope_service6 = findViewById(R.id.spinner_createJob_compressor);
        spinner_scope_service7 = findViewById(R.id.spinner_createJob_line_sets_insulation);
        spinner_scope_service8 = findViewById(R.id.spinner_createJob_unusual_vibration);
        spinner_scope_service9 = findViewById(R.id.spinner_createJob_capacitorPCB);

        spinner_refregerant_suction = findViewById(R.id.spinner_createJob_refregerant);
        spinner_system_leakage = findViewById(R.id.spinner_createJob_systemLeakage);
        spinner_idu_evaporator_coil = findViewById(R.id.spinner_createJob_iduEvaporatorCoil);
        spinner_odu_condencer_coil = findViewById(R.id.spinner_createJob_oduCondencerCoil);
        spinner_piping_installation_connectors = findViewById(R.id.spinner_createJob_pipingInstallationConnectors);
        spinner_drain_system = findViewById(R.id.spinner_createJob_drainSystem);
        spinner_power_control_system = findViewById(R.id.spinner_createJob_powerControlSystem);
        spinner_idu_odu_system = findViewById(R.id.spinner_createJob_iduOduSystem);

        addNewPage = findViewById(R.id.addNewPageCreateJob);
        submitJob = findViewById(R.id.submitCreateJob);

        items1 = new ArrayList<>();
        items2 = new ArrayList<>();
        items3 = new ArrayList<>();
        items4 = new ArrayList<>();
        items5 = new ArrayList<>();
        items6 = new ArrayList<>();
        items7 = new ArrayList<>();
        items8 = new ArrayList<>();
        items9 = new ArrayList<>();
        items1 = Arrays.asList(getResources().getStringArray(R.array.select_scope_services_mode));
        items2 = Arrays.asList(getResources().getStringArray(R.array.select_refregerant_suction_pressor_mode));
        items3 = Arrays.asList(getResources().getStringArray(R.array.select_system_leakage_mode));
        items4 = Arrays.asList(getResources().getStringArray(R.array.select_iduEvaporator_coil_mode));
        items5 = Arrays.asList(getResources().getStringArray(R.array.select_piping_installation_connector_mode));
        items6 = Arrays.asList(getResources().getStringArray(R.array.select_power_control_system_mode));
        items9 = Arrays.asList(getResources().getStringArray(R.array.select_idu_and_odu_system_mode));
        items7 = Arrays.asList(getResources().getStringArray(R.array.hours_array));
        items8 = Arrays.asList(getResources().getStringArray(R.array.mint_array));

        spinner_scope_service1.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service2.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service3.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service4.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service5.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service6.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service7.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service8.setAdapter(setSpinnerInspection(mContext, items1));
        spinner_scope_service9.setAdapter(setSpinnerInspection(mContext, items1));

        spinner_refregerant_suction.setAdapter(setSpinner(mContext, items2));
        spinner_system_leakage.setAdapter(setSpinner(mContext, items3));
        spinner_idu_evaporator_coil.setAdapter(setSpinner(mContext, items4));
        spinner_odu_condencer_coil.setAdapter(setSpinner(mContext, items4));
        spinner_piping_installation_connectors.setAdapter(setSpinner(mContext, items5));
        spinner_drain_system.setAdapter(setSpinner(mContext, items5));
        spinner_power_control_system.setAdapter(setSpinner(mContext, items6));
        spinner_idu_odu_system.setAdapter(setSpinner(mContext, items9));

        addNewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToJobItems();
            }
        });

        submitJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ac_no.getText().toString().equals("") && !ac_location.getText().toString().equals("")){
                    addToJobItems();
                }
                if (jsonArray.length() <= 0) {
                    addToJobItems();
                    return;
                }
                Log.e("JSONArray--->", jsonArray.toString());
                final AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
                myView = LayoutInflater.from(mContext).inflate(R.layout.submit_create_job_confim, null);
                myDialog.setView(myView);

                Button button = myView.findViewById(R.id.submitCreateJobConfirm);
                final EditText et_name = myView.findViewById(R.id.create_job_et_confirm_clientNameId);
                final Spinner sp_time_inH = myView.findViewById(R.id.spinner_createJob_confirm_tih);
                final Spinner sp_time_inM = myView.findViewById(R.id.spinner_createJob_confirm_tim);
                final Spinner sp_time_outH = myView.findViewById(R.id.spinner_createJob_confirm_toh);
                final Spinner sp_time_outM = myView.findViewById(R.id.spinner_createJob_confirm_tom);
                final EditText et_distance = myView.findViewById(R.id.create_job_et_confirm_distance);

                sp_time_inH.setAdapter(setSpinner(mContext, items7));
                sp_time_inM.setAdapter(setSpinner(mContext, items8));
                sp_time_outH.setAdapter(setSpinner(mContext, items7));
                sp_time_outM.setAdapter(setSpinner(mContext, items8));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = et_name.getText().toString();
                        String time_in_hour = sp_time_inH.getSelectedItem().toString().trim();
                        String time_in_mint = sp_time_inM.getSelectedItem().toString().trim();
                        String time_out_hour = sp_time_outH.getSelectedItem().toString().trim();
                        String time_out_mint = sp_time_outM.getSelectedItem().toString().trim();
                        String distance = et_distance.getText().toString();


                        if (TextUtils.isEmpty(name)) {
                            et_name.setError("Please enter ticket NO");
                            et_name.requestFocus();
                            return;
                        }
                        if (time_in_hour.equals("HH")) {
                            Toast.makeText(mContext, "Please select your time in hour", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (time_in_mint.equals("MM")) {
                            Toast.makeText(mContext, "Please select your time in minuts", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (time_out_hour.equals("HH")) {
                            Toast.makeText(mContext, "Please select your time out hour", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (time_out_mint.equals("MM")) {
                            Toast.makeText(mContext, "Please select your time out minuts", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(distance)) {
                            et_distance.setError("Please enter distance in km");
                            et_distance.requestFocus();
                            return;
                        }

                        String time_in_amOrpm = Integer.parseInt(time_in_hour) > 12  ? "PM" : "AM";
                        String time_out_amOrpm = Integer.parseInt(time_out_hour) > 12  ? "PM" : "AM";;

                        long timeIn_inMint = Long.parseLong(time_in_hour) * 60 + Long.parseLong(time_in_mint);
                        long timeOut_inMint = Long.parseLong(time_out_hour) * 60 + Long.parseLong(time_out_mint);

                        long totaltime = timeOut_inMint - timeIn_inMint;
                        if (totaltime < 0){
                            long day_total_mints = 1440;
                            totaltime = day_total_mints + totaltime;
                        }
                        String time = String.valueOf(totaltime);
                        String strTimeIn = time_in_hour + ":" + time_in_mint + " " + time_in_amOrpm;
                        String strTimeOut = time_out_hour + ":" + time_out_mint + " " + time_out_amOrpm;

                        submitJobRequest(name, strTimeIn, strTimeOut, distance, time);
                    }
                });

                dialog = myDialog.create();
                dialog.show();
            }
        });

    }


    @NonNull
    private CreateJobCustomSpinnerAdapter setSpinner(Context context, List<String> items) {
        return new CreateJobCustomSpinnerAdapter(context, items);
    }

    @NonNull
    private TicketSpinnerAdapter setSpinnerInspection(Context context, List<String> items) {
        return new TicketSpinnerAdapter(context, items);
    }

    public void addToJobItems() {

        final String ac_no_ = ac_no.getText().toString();
        final String ac_location_ = ac_location.getText().toString();

        if (TextUtils.isEmpty(ac_no_)) {
            ac_no.setError("Please enter AC no");
            ac_no.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ac_location_)) {
            ac_location.setError("Please enter AC location");
            ac_location.requestFocus();
            return;
        }

        String sos_filters = "",
                sos_evaporator_coil = "",
                sos_blower_assambly = "",
                sos_condencer_coil = "",
                sos_fan_motor = "",
                sos_compressor = "",
                sos_line_sets_insulation = "",
                sos_unusual_vibration = "",
                sos_capacitor_electrical = "",
                rsp_type = "",
                sl_type = "",
                idu_ec_type = "",
                odu_cc_type = "",
                pic_type = "",
                ds_type = "",
                pcs_type = "",
                idu_odu_sys_type = "",
                general_comment = "";

        if (!spinner_scope_service1.getSelectedItem().toString().equals("Select One")) {
            sos_filters = spinner_scope_service1.getSelectedItem().toString();
        }
        if (!spinner_scope_service2.getSelectedItem().toString().equals("Select One")) {
            sos_evaporator_coil = spinner_scope_service2.getSelectedItem().toString();
        }
        if (!spinner_scope_service3.getSelectedItem().toString().equals("Select One")) {
            sos_blower_assambly = spinner_scope_service3.getSelectedItem().toString();
        }
        if (!spinner_scope_service4.getSelectedItem().toString().equals("Select One")) {
            sos_condencer_coil = spinner_scope_service4.getSelectedItem().toString();
        }
        if (!spinner_scope_service5.getSelectedItem().toString().equals("Select One")) {
            sos_fan_motor = spinner_scope_service5.getSelectedItem().toString();
        }
        if (!spinner_scope_service6.getSelectedItem().toString().equals("Select One")) {
            sos_compressor = spinner_scope_service6.getSelectedItem().toString();
        }
        if (!spinner_scope_service7.getSelectedItem().toString().equals("Select One")) {
            sos_line_sets_insulation = spinner_scope_service7.getSelectedItem().toString();
        }
        if (!spinner_scope_service8.getSelectedItem().toString().equals("Select One")) {
            sos_unusual_vibration = spinner_scope_service8.getSelectedItem().toString();
        }
        if (!spinner_scope_service9.getSelectedItem().toString().equals("Select One")) {
            sos_capacitor_electrical = spinner_scope_service9.getSelectedItem().toString();
        }
        if (!spinner_refregerant_suction.getSelectedItem().toString().equals("Select One")) {
            rsp_type = spinner_refregerant_suction.getSelectedItem().toString();
        }
        if (!spinner_system_leakage.getSelectedItem().toString().equals("Select One")) {
            sl_type = spinner_system_leakage.getSelectedItem().toString();
        }
        if (!spinner_idu_evaporator_coil.getSelectedItem().toString().equals("Select One")) {
            idu_ec_type = spinner_idu_evaporator_coil.getSelectedItem().toString();
        }
        if (!spinner_odu_condencer_coil.getSelectedItem().toString().equals("Select One")) {
            odu_cc_type = spinner_odu_condencer_coil.getSelectedItem().toString();
        }
        if (!spinner_piping_installation_connectors.getSelectedItem().toString().equals("Select One")) {
            pic_type = spinner_piping_installation_connectors.getSelectedItem().toString();
        }
        if (!spinner_drain_system.getSelectedItem().toString().equals("Select One")) {
            ds_type = spinner_drain_system.getSelectedItem().toString();
        }
        if (!spinner_power_control_system.getSelectedItem().toString().equals("Select One")) {
            pcs_type = spinner_power_control_system.getSelectedItem().toString();
        }
        if (!spinner_idu_odu_system.getSelectedItem().toString().equals("Select One")) {
            idu_odu_sys_type = spinner_idu_odu_system.getSelectedItem().toString();
        }
        general_comment = generalComment.getText().toString().trim();

        if (!TextUtils.isEmpty(ac_no_) && !TextUtils.isEmpty(ac_location_)) {

            if (!notNull(
                    sos_filters,
                    sos_evaporator_coil,
                    sos_blower_assambly,
                    sos_condencer_coil,
                    sos_fan_motor,
                    sos_compressor,
                    sos_line_sets_insulation,
                    sos_unusual_vibration,
                    sos_capacitor_electrical,
                    rsp_type,
                    sl_type,
                    idu_ec_type,
                    odu_cc_type,
                    pic_type,
                    ds_type,
                    pcs_type,
                    idu_odu_sys_type,
                    general_comment)) {
                AlertDisplayer alertDisplayer = new AlertDisplayer("Warninig", "Atleast select one drop down OR remove AC No and Location fields", CreateJobFormActivity.this);
                alertDisplayer.alertDisplayer2();
                return;
            }
        }

        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("ac_no", ac_no_);
            jsonObj.put("ac_location", ac_location_);
            jsonObj.put("sos_filters", sos_filters);
            jsonObj.put("sos_evaporator_coil", sos_evaporator_coil);
            jsonObj.put("sos_blower_assambly", sos_blower_assambly);
            jsonObj.put("sos_condencer_coil", sos_condencer_coil);
            jsonObj.put("sos_fan_motor", sos_fan_motor);
            jsonObj.put("sos_compressor", sos_compressor);
            jsonObj.put("sos_line_sets_insulation", sos_line_sets_insulation);
            jsonObj.put("sos_unusual_vibration", sos_unusual_vibration);
            jsonObj.put("sos_capacitor_electrical", sos_capacitor_electrical);
            jsonObj.put("rsp_type", rsp_type);
            jsonObj.put("sl_type", sl_type);
            jsonObj.put("idu_ec_type", idu_ec_type);
            jsonObj.put("odu_cc_type", odu_cc_type);
            jsonObj.put("pic_type", pic_type);
            jsonObj.put("ds_type", ds_type);
            jsonObj.put("pcs_type", pcs_type);
            jsonObj.put("idu_odu_sys_type", idu_odu_sys_type);
            jsonObj.put("general_comment", general_comment);

            jsonArray.put(jsonObj);
            Log.d("JSONObject -> JSONArray", jsonArray.toString());


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JASON OBJECT------->", e.getMessage());
        }


        ac_no.setText("");
        ac_location.setText("");
        spinner_scope_service1.setSelection(0);
        spinner_scope_service2.setSelection(0);
        spinner_scope_service3.setSelection(0);
        spinner_scope_service4.setSelection(0);
        spinner_scope_service5.setSelection(0);
        spinner_scope_service6.setSelection(0);
        spinner_scope_service7.setSelection(0);
        spinner_scope_service8.setSelection(0);
        spinner_scope_service9.setSelection(0);
        spinner_refregerant_suction.setSelection(0);
        spinner_system_leakage.setSelection(0);
        spinner_idu_evaporator_coil.setSelection(0);
        spinner_odu_condencer_coil.setSelection(0);
        spinner_piping_installation_connectors.setSelection(0);
        spinner_drain_system.setSelection(0);
        spinner_power_control_system.setSelection(0);
        spinner_idu_odu_system.setSelection(0);

        for (int i = 0; i < 2; i++) {
            nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
        }

    }

    private void submitJobRequest(final String client_id_name, final String time_in, final String time_out, final String distance, final String totalTime) {

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
                dialog.cancel();
                emptyJSONArray(jsonArray);
                Intent i = new Intent(CreateJobFormActivity.this, ShowSubmitJobActivity.class);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject getJobInJson = obj.getJSONObject("submit_job");
                        JSONArray jobItem = getJobInJson.getJSONArray("job_items");

                        i.putExtra("name", getJobInJson.getString("ticket_no"));
                        i.putExtra("timeIn", getJobInJson.getString("time_in"));
                        i.putExtra("timeOut", getJobInJson.getString("time_out"));
                        i.putExtra("distance", getJobInJson.getString("distance"));
                        i.putExtra("arrayOfitems", jobItem.toString());
                        startActivity(i);

                    } else {
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.wtf(TAG, e.getMessage());
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                final String technician_id = String.valueOf(SharedPrefManagerTechnician.getInstance(mContext).getTechnician().getId());

                params.put("technician_id", technician_id);
                params.put("ticket_no", client_id_name);
                params.put("time_in", time_in);
                params.put("time_out", time_out);
                params.put("distance", distance);
                params.put("working_time", totalTime);
                params.put("items", jsonArray.toString());

                return requestHandler.sendPostRequest(Apis.JOB_SUBMIT_FORM_URL, params);
                //returing the response
            }
        }

        Submit s = new Submit();
        s.execute();

    }

    @Override
    public void onBackPressed() {

        if (jsonArray.length()>0) {
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(CreateJobFormActivity.this)
                    .setTitle("Note")
                    .setMessage("Your form save data will be lost after exit")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emptyJSONArray(jsonArray);
                            onBackPressed();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            android.app.AlertDialog ok = builder.create();
            ok.show();
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (emptyJSONArray(jsonArray)) {
            super.onDestroy();
        } else {
            Log.d("JSONArray---->", "array not clear" + jsonArray.toString());
        }
    }

    boolean notNull(String... args) {
        for (String arg : args) {

            if (!arg.equals("")) {
                return true;
            }
        }
        return false;
    }

    boolean emptyJSONArray(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            for (int k = 0; k < jsonArray.length(); k++) {
                jsonArray.remove(k);
            }
        }
        return true;
    }

}

