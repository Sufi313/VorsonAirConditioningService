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
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.CreateJobListAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.JobFormModel;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateJobListActivity extends AppCompatActivity {

    private List<JobFormModel> list;
    private RecyclerView recyclerView;
    private Context context;
    private ImageView goBack;
    private TextView ADerror;
    private SearchView searchView;
    private CreateJobListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_list);
        Toolbar toolbar = findViewById(R.id.toolbarCreateJobListActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        goBack = findViewById(R.id.goBackCreateJobListActivity);
        ADerror = findViewById(R.id.CreateJobListActivity_noFonudText);
        recyclerView = findViewById(R.id.createJobListActivity_recyclerView);
        context = CreateJobListActivity.this;
        list = new ArrayList<>();
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        LoadList ul = new LoadList();
        ul.execute();
        adapter = new CreateJobListAdapter(context,list);

    }


    class LoadList extends AsyncTask<Void, Void, String> {


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

                    if (obj.getString("result").equals("fail")) {
                        ADerror.setText(obj.getString("message"));
                        ADerror.setVisibility(View.VISIBLE);
                        return;
                    }


                    JSONArray invoiceArray = obj.getJSONArray("jobs");
                    for (int i = 0; i < invoiceArray.length(); i++) {

                        //getting product object from json array
                        obj = invoiceArray.getJSONObject(i);

                        list.add(new JobFormModel(
                                obj.getInt("id"),
                                obj.getString("client_id_name"),
                                obj.getString("created_at"),
                                obj.getString("time_in"),
                                obj.getString("time_out"),
                                obj.getString("distance"),
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
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            String id = String.valueOf(SharedPrefManagerTechnician.getInstance(context).getTechnician().getId());
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("technician_id", id);

            //returing the response
            return requestHandler.sendPostRequest(Apis.GET_CREATE_JOB_LIST_URL, params);
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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

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
        startActivity(new Intent(context,TechnicianDashboardActivity.class));
    }

}
