package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.AssignJobListAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.AssignJobModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.GetData;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class AssignJobActivity extends AppCompatActivity implements GetData.GetDataListener{

    private List<AssignJobModule> list;
    private RecyclerView recyclerView;
    private Context context;
    private ImageView goBack;
    private TextView ADerror;
    private SearchView searchView;
    private AssignJobListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_job);

        Toolbar toolbar = findViewById(R.id.toolbarAssignJob);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        list = new ArrayList<>();
        context = AssignJobActivity.this;
        recyclerView = findViewById(R.id.assignJob_recyclerView);
        ADerror = findViewById(R.id.assignJob_noFonudText);
        goBack = findViewById(R.id.goBackAssignJob);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        String id = String.valueOf(SharedPrefManagerTechnician.getInstance(context).getTechnician().getId());
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        new GetData(Apis.ASS_JOB_LIST_URL,params,1,this).executeOnExecutor(THREAD_POOL_EXECUTOR);

        adapter = new AssignJobListAdapter(context,list);


    }

    @Override
    public void getDownloadData(String result, int request) {
        try {

            //converting response to json object
            JSONObject obj = new JSONObject(result);

            //if no error in response
            if (!obj.getBoolean("error")) {

                if (obj.getString("result").equals("fail")){
                    ADerror.setText(obj.getString("message"));
                    ADerror.setVisibility(View.VISIBLE);
                    return;
                }


                JSONArray invoiceArray = obj.getJSONArray("jobs");
                for (int i = 0; i < invoiceArray.length(); i++) {

                    //getting product object from json array
                    obj = invoiceArray.getJSONObject(i);

                    list.add(new AssignJobModule(
                            obj.getInt("id"),
                            obj.getInt("complaint_id"),
                            obj.getString("client_name"),
                            obj.getString("service_type"),
                            obj.getString("service_priority"),
                            obj.getString("phone_number"),
                            obj.getString("message"),
                            obj.getString("client_address"),
                            obj.getInt("status")

                    ));

                }
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            } else {
                Toast.makeText(getApplicationContext(), "Error While Download", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.wtf("JSON ERROR LOGIN", e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        finish();
        startActivity(new Intent(AssignJobActivity.this,TechnicianDashboardActivity.class));

    }

}


