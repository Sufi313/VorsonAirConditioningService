package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.CreateJobItemAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.JobFormItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowSubmitJobActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<JobFormItemModel> list;
    private TextView tvName,tvTimeIn,tvTimeOut,tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_submit_job);

        recyclerView = findViewById(R.id.showSubmitJob_recyclerView);
        tvName = findViewById(R.id.showSubmitJob_clientName);
        tvTimeIn = findViewById(R.id.showSubmitJob_timeIn);
        tvTimeOut = findViewById(R.id.showSubmitJob_timeOut);
        tvDistance = findViewById(R.id.showSubmitJob_distance);

        list = new ArrayList<>();

        Intent i = getIntent();

        String name = i.getStringExtra("ticketNo");
        String timeIn = i.getStringExtra("timeIn");
        String timeOut = i.getStringExtra("timeOut");
        String distance = i.getStringExtra("distance");
        String jsonArray = i.getStringExtra("arrayOfitems");

        tvName.setText(name);
        tvTimeIn.setText(timeIn);
        tvTimeOut.setText(timeOut);
        tvDistance.setText(distance);


        try {
            JSONArray array = new JSONArray(jsonArray);
            for (int j = 0; j < array.length(); j++) {

               JSONObject obj = array.getJSONObject(j);

                list.add(new JobFormItemModel(

                        obj.getString("ac_no"),
                        obj.getString("ac_location"),
                        obj.getString("sos_filter"),
                        obj.getString("sos_evaporator_coil"),
                        obj.getString("sos_blower_assambly"),
                        obj.getString("sos_condencer_coil"),
                        obj.getString("sos_fan_motor"),
                        obj.getString("sos_compressor"),
                        obj.getString("sos_line_sets_insulation"),
                        obj.getString("sos_unusual_vibration"),
                        obj.getString("sos_capacitor_electrical"),
                        obj.getString("rsp_type"),
                        obj.getString("sl_type"),
                        obj.getString("idu_ec_type"),
                        obj.getString("odu_cc_type"),
                        obj.getString("pic_type"),
                        obj.getString("ds_type"),
                        obj.getString("pcs_type"),
                        obj.getString("idu_odu_sys_type"),
                        obj.getString("general_comment")

                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setNestedScrollingEnabled(false);
        CreateJobItemAdapter createJobItemAdapter = new CreateJobItemAdapter(this,list);
        recyclerView.setAdapter(createJobItemAdapter);
        createJobItemAdapter.notifyDataSetChanged();
    }
}
