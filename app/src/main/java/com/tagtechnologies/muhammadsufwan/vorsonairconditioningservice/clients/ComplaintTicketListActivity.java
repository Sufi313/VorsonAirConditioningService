package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TicketListAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TicketModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComplaintTicketListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalComp, tvSolvedComp, tvPendingComp, tvCancelComp, tvNoTextFoundText;
    private ProgressBar progressBar;
    private TicketListAdapter adapter;
    private List<TicketModule> ticketList;
    private int s_comp = 0;
    private int p_comp = 0;
    private int c_comp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_ticket_list);
        recyclerView = findViewById(R.id.recyclerViewComplaintTicketList);
        progressBar = findViewById(R.id.progressBarComplaintTicketList);
        tvTotalComp = findViewById(R.id.tvTCComplaintTicketList);
        tvSolvedComp = findViewById(R.id.tvSCComplaintTicketList);
        tvPendingComp = findViewById(R.id.tvPCComplaintTicketList);
        tvCancelComp = findViewById(R.id.tvCCComplaintTicketList);
        tvNoTextFoundText = findViewById(R.id.textNotFoundComplaintTicketList);

        ticketList = new ArrayList<>();
        adapter = new TicketListAdapter(ComplaintTicketListActivity.this, ticketList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        GetComplaintlist();

    }

    public void GetComplaintlist() {

        class GetList extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        if (obj.getString("result").equals("fail")) {
                            tvNoTextFoundText.setText(obj.getString("message"));
                            tvNoTextFoundText.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            return;
                        }

                        int    id = 0;
                        int    invoice_id = 0;
                        int    status = 0;
                        String help = "";
                        String message = "";
                        String created_at = "";
                        String task_ended_at = "";
                        String tech_name = "";
                        String tech_number = "";
                        JSONArray invoiceArray = obj.getJSONArray("tickect");
                        for (int i = 0; i < invoiceArray.length(); i++) {

                            //getting product object from json array
                            obj = invoiceArray.getJSONObject(i);
                            if (obj.has("id")) {
                                id = obj.getInt("id");
                            }
                            if (obj.has("invoice_id")) {
                                invoice_id = obj.getInt("invoice_id");
                            }
                            if (obj.has("status")) {
                                status = obj.getInt("status");
                            }
                            if (obj.has("help")) {
                                help = obj.getString("help");
                            }
                            if (obj.has("message")) {
                                message = obj.getString("message");
                            }
                            if (obj.has("created_at")) {
                                created_at = obj.getString("created_at");
                            }
                            if (obj.has("task_ended_at")) {
                                task_ended_at = obj.getString("task_ended_at");
                            }
                            if (obj.has("assign_tech_name")) {
                                tech_name = obj.getString("assign_tech_name");
                            }
                            if (obj.has("assign_tech_number")) {
                                tech_number = obj.getString("assign_tech_number");
                            }

                            ticketList.add(new TicketModule(id,invoice_id,status,help,message,created_at,task_ended_at,tech_name,tech_number));

                            id = 0;
                            invoice_id = 0;
                            status = 0;
                            help = "";
                            message = "";
                            created_at = "";
                            task_ended_at = "";
                            tech_name = "";
                            tech_number = "";

                        }

                        for(TicketModule d : ticketList){
                            if(d.getStatus()== 0){
                                p_comp++;
                            }
                            if(d.getStatus()== 1){
                                c_comp++;
                            }
                            if(d.getStatus()== 2){
                                s_comp++;
                            }
                        }

                        tvTotalComp.append(String.valueOf(ticketList.size()));
                        tvSolvedComp.append(String.valueOf(s_comp));
                        tvPendingComp.append(String.valueOf(p_comp));
                        tvCancelComp.append(String.valueOf(c_comp));

                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();




                    } else {
                        Toast.makeText(getApplicationContext(), "No Ticket List found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR FETCH TICKET LIST", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                String client_id = String.valueOf(SharedPrefManagerClient.getInstance(ComplaintTicketListActivity.this).getClient().getId());

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("client_id", client_id);

                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_GET_CLIENT_TICKET_LIST, params);
            }
        }
        GetList gl = new GetList();
        gl.execute();


    }
}
