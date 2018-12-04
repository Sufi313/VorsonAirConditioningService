package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidItemInvoiceModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PaymentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryPaidAdapter adapter;
    private List<HistoryPaidModule> paidInvoiceList;
    private List<HistoryPaidItemInvoiceModule> paidInvoiceItem;
    private TextView totalInvoices, totalAmount, nameOfClient;
    private String name, client_id;
    private float totalInvoiceAmount;
    private Context mContext;
    private SearchView searchView;
    private String get_type = "";
    private TextView ADerror;
    private ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);
        Toolbar toolbar = findViewById(R.id.toolbarClientInvoiceList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        ADerror = findViewById(R.id.paymentNotFound);

        goBack = findViewById(R.id.goBackClientInvoiceList);

        Intent intent = getIntent();
        get_type = intent.getStringExtra("get_type");

        totalAmount = findViewById(R.id.totalAmountPaidInvoice);
        totalInvoices = findViewById(R.id.totalPaidInvoiceList);
        nameOfClient = findViewById(R.id.textClientNamePayment);
        recyclerView = findViewById(R.id.serviceInvoiceList);
        mContext = this;

        paidInvoiceList = new ArrayList<>();
        paidInvoiceItem = new ArrayList<>();
        adapter = new HistoryPaidAdapter(mContext, paidInvoiceList, paidInvoiceItem);
        whiteNotificationBar(recyclerView);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        name = SharedPrefManagerClient.getInstance(this).getClient().getName();
        client_id = String.valueOf(SharedPrefManagerClient.getInstance(this).getClient().getId());
        nameOfClient.setText(name);

        getInvoices gi = new getInvoices();
        gi.execute();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    class getInvoices extends AsyncTask<Void, Void, String> {

        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBarInvoiceActivity);
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

                    if (obj.getString("result").equals("fail")){
                        ADerror.setText(obj.getString("message"));
                        ADerror.setVisibility(View.VISIBLE);
                        return;
                    }


                    JSONArray invoiceArray = obj.getJSONArray("invoice");
                    for (int i = 0; i < invoiceArray.length(); i++) {

                        //getting product object from json array
                        obj = invoiceArray.getJSONObject(i);

                        paidInvoiceList.add(new HistoryPaidModule(
                                obj.getInt("id"),
                                obj.getInt("dn_no"),
                                obj.getInt("status"),
                                obj.getString("invoice_type"),
                                obj.getString("sntn_no"),
                                obj.getString("strn_no"),
                                obj.getString("invoice_no"),
                                obj.getString("created_at"),
                                obj.getString("name_address"),
                                obj.getString("branch_address"),
                                (float) obj.getDouble("subtotal"),
                                (float) obj.getDouble("sales_tax"),
                                (float) obj.getDouble("total"),
                                (float) obj.getDouble("advance"),
                                (float) obj.getDouble("balance_due"),
                                paidInvoiceItem


                        ));
                        JSONArray invoiceItem = obj.getJSONArray("items");

                        for (int j = 0; j < invoiceItem.length(); j++) {

                            obj = invoiceItem.getJSONObject(j);

                            paidInvoiceItem.add(new HistoryPaidItemInvoiceModule(

                                    obj.getInt("id"),
                                    obj.getInt("invoice_id"),
                                    obj.getString("item_code"),
                                    obj.getString("description"),
                                    obj.getString("location"),
                                    obj.getString("quantity"),
                                    obj.getString("rate"),
                                    obj.getString("amount")

                            ));

                        }


                    }

                    for (int k = 0; k < paidInvoiceList.size(); k++) {
                        totalInvoiceAmount += paidInvoiceList.get(k).getTotal();
                    }


                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        DecimalFormat formatter = new DecimalFormat("#,##,###.00");
                        String value = getString(R.string.rupees) + formatter.format(totalInvoiceAmount);
                        totalAmount.setText(value);

                    } else {
                        String value = getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", totalInvoiceAmount));
                        totalAmount.setText(value);
                    }
                    totalInvoices.setText(String.valueOf(invoiceArray.length()));


                } else {
                    Toast.makeText(getApplicationContext(), "No Invoices found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.wtf("JSON ERROR FETCH INVOICE LIST", e.getMessage());
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("client_id", client_id);
            params.put("get_type", get_type);

            //returing the response
            return requestHandler.sendPostRequest(Apis.URL_FOR_SERVICE_PAID_LIST, params);
        }
    }

//    hfkjdhjkfhksdjhfkjhsdkjhf

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
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


}
