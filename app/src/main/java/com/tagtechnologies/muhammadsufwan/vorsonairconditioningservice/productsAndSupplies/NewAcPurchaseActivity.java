package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.productsAndSupplies;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.ModelNewAC;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.SpinnerAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.SharedPrefManagerClient;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.AlertDisplayer;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.PathUtil;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.TicketActivity;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class NewAcPurchaseActivity extends AppCompatActivity {

    private Context mContext;
    private View myView;
    private AlertDialog dialog;
    private static final int REQCODE_FILE = 200;
    private Spinner brandSpin, weightSpin, colorSpin, modelSpin;
    private int brand_id = 0, color_id = 0, weight_id = 0, model_id = 0;
    private String selectedFilePath;
    private String UrlForRequest = "";
    private EditText quantity;
    private Button submit;
    private ImageButton upload;
    private ImageView goBackImage;
    private TextView tvFileName, addmore;

    private String encodedFile = "";
    private String fileName = "";
    private SelectedAcItemAdapter adapter;
    private List<AcSelectedModel> itemList;
    private RecyclerView recyclerView;


    private List<ModelNewAC> brandList;
    private List<ModelNewAC> colorList;
    private List<ModelNewAC> weightList;
    private List<ModelNewAC> modelData;

    private SpinnerAdapter spinnerAdapter;
    private android.app.AlertDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ac_purchase);
        Toolbar toolbar = findViewById(R.id.toolbarNewAcPurchaseActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        loadingDialog = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
        mContext = this;

        goBackImage = findViewById(R.id.goBackNewAcPurchaseActivity);

        goBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addmore = findViewById(R.id.addmore_newAcPurchaseActivity);


        brandSpin = findViewById(R.id.spinnerBrand);
        weightSpin = findViewById(R.id.spinnerWeight);
        colorSpin = findViewById(R.id.spinnerColor);
        modelSpin = findViewById(R.id.spinnerModel);
        quantity = findViewById(R.id.editTextQty);
        tvFileName = findViewById(R.id.tvUploadFileName);

        submit = findViewById(R.id.addToRequestPurchase);
        upload = findViewById(R.id.uploadBtn);
        upload.setEnabled(false);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        111);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        111);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else {
            upload.setEnabled(true);
        }


        itemList = new ArrayList<>();
        brandList = new ArrayList<>();
        colorList = new ArrayList<>();
        weightList = new ArrayList<>();
        modelData = new ArrayList<>();

        getDataForSpinner("brands");
        weightSpin.setEnabled(false);
        colorSpin.setEnabled(false);
        modelSpin.setEnabled(false);


        brandSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (brandList.get(position).getId() > 0) {
                    brand_id = brandList.get(position).getId();
                    getDataForSpinner("weights");
                    weightSpin.setEnabled(true);
                }
                if (brand_id > 0 && color_id > 0 && weight_id > 0) {
                    color_id = colorList.get(position).getId();
                    getModelData(String.valueOf(brand_id),String.valueOf(color_id), String.valueOf(weight_id));
                    modelSpin.setEnabled(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weightSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (weightList.get(position).getId()>0) {
                    weight_id = weightList.get(position).getId();
                    getDataForSpinner("colors");
                    colorSpin.setEnabled(true);
                }
                if (brand_id > 0 && color_id > 0 && weight_id > 0) {
                    color_id = colorList.get(position).getId();
                    getModelData(String.valueOf(brand_id),String.valueOf(color_id), String.valueOf(weight_id));
                    modelSpin.setEnabled(true);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        colorSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (colorList.get(position).getId()> 0) {
                    color_id = colorList.get(position).getId();
                    getModelData(String.valueOf(brand_id),String.valueOf(color_id), String.valueOf(weight_id));
                    modelSpin.setEnabled(true);
                }
                if (brand_id > 0 && color_id > 0 && weight_id > 0) {
                    color_id = colorList.get(position).getId();
                    getModelData(String.valueOf(brand_id),String.valueOf(color_id), String.valueOf(weight_id));
                    modelSpin.setEnabled(true);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modelSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (modelData.get(position).getId() > 0) {
                    model_id = modelData.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model_id == 0 && model_id > modelData.size()-1) {
                    AlertDisplayer alertDisplayer = new AlertDisplayer("Note", "Please Select One Model For Request", mContext);
                    alertDisplayer.alertDisplayer2();
                    return;
                }
                if (TextUtils.isEmpty(quantity.getText().toString())) {
                    quantity.setError("Please enter quantity");
                    quantity.requestFocus();
                    return;
                }


                addItemsToArrayList(model_id,modelData.get(model_id).getName(),quantity.getText().toString());

                if (itemList.size() <= 0) {

                    return;
                }


                viewSelectAcAndGo();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Choose application"), REQCODE_FILE);

            }
        });


        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model_id == 0 && model_id > modelData.size()-1) {
                    AlertDisplayer alertDisplayer = new AlertDisplayer("Note", "Please Select One Item For Request", mContext);
                    alertDisplayer.alertDisplayer2();
                    return;
                }
                if (TextUtils.isEmpty(quantity.getText().toString())) {
                    quantity.setError("Please enter quantity");
                    quantity.requestFocus();
                    return;
                }

                addItemsToArrayList(model_id,modelData.get(model_id).getName(),quantity.getText().toString());

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 111:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    upload.setEnabled(true);
                } else {
                    upload.setEnabled(false);
                }
            break;
        }
    }

    private void viewSelectAcAndGo() {

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
        myView = LayoutInflater.from(mContext).inflate(R.layout.selectd_ac_list_show_layout, null);
        myDialog.setView(myView);

        recyclerView = myView.findViewById(R.id.selected_ac_view_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new SelectedAcItemAdapter(NewAcPurchaseActivity.this, itemList);
        recyclerView.setAdapter(adapter);

        Button goShipping = myView.findViewById(R.id.selected_ac_view_btn);
        goShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();

                finalSubmitRequest();

            }
        });

        dialog = myDialog.create();
        dialog.show();

    }

    public void finalSubmitRequest() {

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
        myView = LayoutInflater.from(mContext).inflate(R.layout.new_ac_shipping_layout, null);
        myDialog.setView(myView);

        final EditText fullnameDialog = myView.findViewById(R.id.etFullnameAcPurchaseDialog);
        final EditText emailPurchase = myView.findViewById(R.id.etEmailAcPurchaseDialog);
        final EditText contectPurchase = myView.findViewById(R.id.etContectAcPurchaseDialog);
        final EditText addressPurchase = myView.findViewById(R.id.etAddressAcPurchaseDialog);

        if(SharedPrefManagerClient.getInstance(mContext).isLoggedIn()){

            fullnameDialog.setText(SharedPrefManagerClient.getInstance(mContext).getClient().getName());
            emailPurchase.setText(SharedPrefManagerClient.getInstance(mContext).getClient().getEmail());
            contectPurchase.setText(SharedPrefManagerClient.getInstance(mContext).getClient().getNumber());

        }

        Button confirmSubmit = myView.findViewById(R.id.dialog_ac_confirm_btn);
        confirmSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String get_name = fullnameDialog.getText().toString().trim();

                if (TextUtils.isEmpty(get_name)) {
                    fullnameDialog.setError("Please enter your name");
                    fullnameDialog.requestFocus();
                    return;
                }

                String get_email = emailPurchase.getText().toString().trim();

                if (TextUtils.isEmpty(get_email)) {
                    emailPurchase.setError("Please enter email");
                    emailPurchase.requestFocus();
                    return;
                }

                String get_contect = contectPurchase.getText().toString().trim();

                if (TextUtils.isEmpty(get_contect)) {
                    contectPurchase.setError("Please enter contect number");
                    contectPurchase.requestFocus();
                    return;
                }

                String get_address = addressPurchase.getText().toString().trim();

                if (TextUtils.isEmpty(get_address)) {
                    addressPurchase.setError("Please enter your address");
                    addressPurchase.requestFocus();
                    return;
                }


                SubmitRequest(get_name,get_email,get_contect,get_address);


                dialog.cancel();
            }
        });

        dialog = myDialog.create();
        dialog.show();

    }

    public void getModelData(final String bid, final String cid, final String wid) {

        class GetData extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loadingDialog.cancel();
                try {
                    JSONObject obj = new JSONObject(s);
                    boolean error = obj.getBoolean("error");
                    if (!error) {

                        modelData.clear();

                        JSONArray object = obj.getJSONArray("model");

                        int fid = 0;
                        String fig = "";

                        modelData.add(new ModelNewAC(fid, fig));

                        for (int i = 0; i < object.length(); i++) {
                            JSONObject size = object.getJSONObject(i);

                            modelData.add(new ModelNewAC(
                                    size.getInt("id"),
                                    size.getString("model")
                            ));

                        }


                        spinnerAdapter = new SpinnerAdapter(NewAcPurchaseActivity.this, modelData);
                        spinnerAdapter.notifyDataSetChanged();
                        modelSpin.setAdapter(spinnerAdapter);

                    } else {
                        Toast.makeText(mContext, "Some error occurred from Color Data" + obj.getString("message"), Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    Log.wtf("ON RESPONES JSON EXCEPTION FOR MODELS ------>", e.getMessage());
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("brand_id", bid);
                params.put("color_id", cid);
                params.put("weight_id", wid);


                return requestHandler.sendPostRequest(Apis.URL_FOR_MODEL, params);
            }
        }

        GetData gd = new GetData();
        gd.execute();


    }


    public void getDataForSpinner(final String Type) {

        class getDataFromServer extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                //creating request parameters
                params.put("get_type", Type);
                UrlForRequest = Apis.URL_FOR_NEW_AC_DATA;
                //returing the response


                return requestHandler.sendPostRequest(UrlForRequest, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog.show();

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                loadingDialog.cancel();
                int id = 0;
                String name = "", image = "";

//                Request For AC Brands

                if (Type == "brands") {

                    try {
                        JSONObject obj = new JSONObject(s);
                        boolean error = obj.getBoolean("error");

                        //if no error in response
                        if (!error) {
                            //getting the user from the response
                            JSONArray jsonArray = obj.getJSONArray("output");

                            brandList.add(new ModelNewAC(id, name));

                            //traversing through all the object
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject size = jsonArray.getJSONObject(i);

                                //adding the product to product list
                                brandList.add(new ModelNewAC(
                                        size.getInt("id"),
                                        size.getString("name")

                                ));
                            }
                            spinnerAdapter = new SpinnerAdapter(NewAcPurchaseActivity.this, brandList);
                            spinnerAdapter.notifyDataSetChanged();
                            brandSpin.setAdapter(spinnerAdapter);

                        } else {
                            Toast.makeText(mContext, "Some error occurred from Color Data" + obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Log.wtf("ON RESPONES JSON EXCEPTION FOR BRANDS ------>", e.getMessage());
                    }

                }

//                Request For AC Colors

                if (Type == "colors") {

                    colorList.clear();

                    try {
                        JSONObject obj = new JSONObject(s);
                        boolean error = obj.getBoolean("error");

                        //if no error in response
                        if (!error) {
                            //getting the user from the response
                            JSONArray jsonArray = obj.getJSONArray("output");

                            colorList.add(new ModelNewAC(id, name, image));

                            //traversing through all the object
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject size = jsonArray.getJSONObject(i);

                                //adding the product to product list
                                colorList.add(new ModelNewAC(
                                        size.getInt("id"),
                                        size.getString("name"),
                                        size.getString("type")

                                ));
                            }
                            spinnerAdapter = new SpinnerAdapter(NewAcPurchaseActivity.this, colorList);
                            spinnerAdapter.notifyDataSetChanged();
                            colorSpin.setAdapter(spinnerAdapter);
                        } else {
                            Toast.makeText(mContext, "Some error occurred from Color Data" + obj.getString("message"), Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        Log.wtf("ON RESPONES JSON EXCEPTION FOR COLOR ------>", e.getMessage());
                    }
                }

//                Requset for Ac Weight

                if (Type == "weights") {

                    weightList.clear();

                    try {
                        JSONObject obj = new JSONObject(s);
                        boolean error = obj.getBoolean("error");

                        //if no error in response
                        if (!error) {
                            //getting the user from the response
                            JSONArray jsonArray = obj.getJSONArray("output");

                            weightList.add(new ModelNewAC(id, name));

                            //traversing through all the object
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject size = jsonArray.getJSONObject(i);

                                //adding the product to product list
                                weightList.add(new ModelNewAC(
                                        size.getInt("id"),
                                        size.getString("name")

                                ));

                            }
                            spinnerAdapter = new SpinnerAdapter(NewAcPurchaseActivity.this, weightList);
                            spinnerAdapter.notifyDataSetChanged();
                            weightSpin.setAdapter(spinnerAdapter);
                        } else {
                            Toast.makeText(mContext, "Some error occurred for get weight data " + obj.getString("message"), Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        Log.wtf("ON RESPONES JSON EXCEPTION FOR WEIGHT ------>", e.getMessage());
                    }
                }


            }
        }

        //executing the async task
        getDataFromServer gcfs = new getDataFromServer();
        gcfs.execute();
    }


    public void SubmitRequest(final String _name, final String _email, final String _contect, final String _address) {




        class SubmitData extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog.show();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                loadingDialog.cancel();

                try {
                    JSONObject obj = new JSONObject(s);
                    boolean error = obj.getBoolean("error");

                    //if no error in response
                    if (!error) {

                        Toast.makeText(mContext, obj.getString("result"), Toast.LENGTH_SHORT).show();
                        itemList.clear();

                    }else{
                        Toast.makeText(mContext, obj.getString("result"), Toast.LENGTH_SHORT).show();
                    }




                } catch (JSONException e) {
                    Log.wtf("ON RESPONES JSON EXCEPTION SUBMIT ------>", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                if (SharedPrefManagerClient.getInstance(mContext).isLoggedIn()){

                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < itemList.size(); i++){
                        try {
                            JSONObject jsonObj = new JSONObject();

                                jsonObj.put("model_id", String.valueOf(itemList.get(i).getId()));


                                jsonObj.put("quantity", String.valueOf(itemList.get(i).getQuantity()));

                            if (!itemList.get(i).getEncodedFile().equals("")){
                                jsonObj.put("file", String.valueOf(itemList.get(i).getEncodedFile()));
                            }else{
                                jsonObj.put("file", "");
                            }
                            if (!itemList.get(i).getFileName().equals("")){
                                jsonObj.put("file_formate", String.valueOf(itemList.get(i).getFileName()));
                            }else{
                                jsonObj.put("file_formate", "");
                            }
                            jsonArray.put(jsonObj);
                            Log.wtf("JSON ARRAY" + String.valueOf(i), jsonArray.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    params.put("client_id",String.valueOf(SharedPrefManagerClient.getInstance(mContext).getClient().getId()));
                    params.put("full_name",_name);
                    params.put("contact_no",_contect);
                    params.put("email",_email);
                    params.put("address",_address);
                    params.put("items",jsonArray.toString());

                }

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < itemList.size(); i++){
                    try {
                        JSONObject jsonObj = new JSONObject();

                            jsonObj.put("model_id", String.valueOf(itemList.get(i).getId()));


                            jsonObj.put("quantity", itemList.get(i).getQuantity());

                        if (!itemList.get(i).getEncodedFile().equals("")){
                            jsonObj.put("file", String.valueOf(itemList.get(i).getEncodedFile()));
                        }else{
                            jsonObj.put("file", "");
                        }
                        if (!itemList.get(i).getFileName().equals("")){
                            jsonObj.put("file_formate", String.valueOf(itemList.get(i).getFileName()));
                        }else{
                            jsonObj.put("file_formate", "");
                        }
                        jsonArray.put(jsonObj);
                        Log.wtf("JSON ARRAY" + String.valueOf(i), jsonArray.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                params.put("client_id","0");
                params.put("full_name",_name);
                params.put("contact_no",_contect);
                params.put("email",_email);
                params.put("address",_address);
                params.put("items",jsonArray.toString());


                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_FOR_SUBMIT_ORDER_AC, params);
            }
        }

        //executing the async task
        SubmitData sd = new SubmitData();
        sd.execute();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode == REQCODE_FILE && resultCode == RESULT_OK
                    && data != null) {

                Uri selectedFileUri = data.getData();
                selectedFilePath = PathUtil.getPath(this, selectedFileUri);
                Log.i("PONKA", "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    encodedFile = encodeFileToBase64Binary(selectedFilePath);

                    String[] parts = selectedFilePath.split("/");
                    fileName = parts[parts.length - 1];
                    tvFileName.setText(fileName);

                }


            } else {
                Toast.makeText(mContext, "You have not picked file",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
            Log.wtf("ASDFGH----->", e.getMessage());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addItemsToArrayList(int id,String model,String quantity_) {

        itemList.add(new AcSelectedModel(id,model, quantity_, fileName, encodedFile));
        tvFileName.setText("Upload your specification file");

        fileName = "";
        encodedFile = "";
        quantity.setText("");
        colorList.clear();
        weightList.clear();
        modelData.clear();
        weightSpin.setAdapter(spinnerAdapter);
        colorSpin.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
        colorSpin.setEnabled(false);
        weightSpin.setEnabled(false);
        modelSpin.setEnabled(false);
        brand_id = 0;
        color_id = 0;
        weight_id = 0;
        model_id = 0;

    }

    private String encodeFileToBase64Binary(String fileName)
            throws IOException {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encodeBase64(bytes);
        String encodedString = new String(encoded);

        return encodedString;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

}
