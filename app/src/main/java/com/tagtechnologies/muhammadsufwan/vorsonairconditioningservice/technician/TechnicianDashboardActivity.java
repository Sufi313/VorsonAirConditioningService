package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.LounchActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments.ForHelpTechnicianFragment;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments.TechniTimesFragment;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class TechnicianDashboardActivity extends AppCompatActivity {

    private Fragment fragment;
    private LinearLayout createJobForm,assignJobs,jobFormList,forTechHelp,workingTime,techniProfile,techHelpList;
    private ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbarTechnicianDashboard);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        goback = findViewById(R.id.goBackTechnicianDasBoard);
        createJobForm = findViewById(R.id.createJobForm);
        forTechHelp = findViewById(R.id.forHelpTechnician);
        jobFormList = findViewById(R.id.jobFormList);
        assignJobs = findViewById(R.id.assignJobs);
        workingTime = findViewById(R.id.workingTime);
        techniProfile = findViewById(R.id.techni_profile);
        techHelpList = findViewById(R.id.helpList);


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        assignJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TechnicianDashboardActivity.this,AssignJobActivity.class));
            }
        });

        createJobForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TechnicianDashboardActivity.this,CreateJobFormActivity.class));
            }
        });
        jobFormList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TechnicianDashboardActivity.this,CreateJobListActivity.class));
            }
        });

        forTechHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ForHelpTechnicianFragment();
                setFragment(fragment);
            }
        });
        workingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new TechniTimesFragment();
                setFragment(fragment);
            }
        });

        techniProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TechnicianDashboardActivity.this,TechnicianProfile.class));
            }
        });

        techHelpList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TechnicianDashboardActivity.this,TechHelpList.class));
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(android.R.anim.fade_out, android.R.anim.fade_out);
            ft.remove(fragment);
            ft.commit();
            FrameLayout layout = findViewById(R.id.screen_area_technician);
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setVisibility(View.VISIBLE);
            }
            fragment = null;
        } else {
            finish();
            startActivity(new Intent(TechnicianDashboardActivity.this, LounchActivity.class));
        }
    }

    private void setFragment(Fragment fragment){
        if (fragment != null) {
            //((FrameLayout)findViewById(R.id.screen_area_technician)).removeAllViews();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_in);
            ft.replace(R.id.screen_area_technician, fragment);
            ft.commit();
            FrameLayout layout = findViewById(R.id.screen_area_technician);
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setVisibility(View.GONE);
            }
        }
    }

}
