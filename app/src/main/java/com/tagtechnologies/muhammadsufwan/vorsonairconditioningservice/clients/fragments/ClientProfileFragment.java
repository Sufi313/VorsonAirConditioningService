package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.Client;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.LoginClientActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.SharedPrefManagerClient;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.AlertDisplayer;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.InternetCheck;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.PathUtil;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.WIFI_SERVICE;
import static com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments.ForHelpTechnicianFragment.REQCODE;


public class ClientProfileFragment extends Fragment {

    private Calendar calendar;
    private EditText dateView, address, email, mobile, B_name, B_Activity, B_phone, B_address,name;
    private Spinner gender;
    private int year, month, day;
    private ImageView profileImageView;
    private FloatingActionButton fab;
    private Button saveBtn;
    private Context context;
    private String encodeImage;
    public static final int REQCODE_CP = 111;
    private List<String> gender_items;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        dateView = view.findViewById(R.id.etdob_CPF);
        address = view.findViewById(R.id.etliving_Address_CPF);
        name = view.findViewById(R.id.etName_CPF);
        email = view.findViewById(R.id.etEmail_CPF);
        B_name = view.findViewById(R.id.etBusiness_name_CPF);
        B_Activity = view.findViewById(R.id.etBusiness_activity_CPF);
        B_phone = view.findViewById(R.id.etBusiness_phone_CPF);
        B_address = view.findViewById(R.id.etBusiness_address_CPF);
        mobile = view.findViewById(R.id.etMobile_CPF);
        gender = view.findViewById(R.id.sprGender_CPF);
        saveBtn = view.findViewById(R.id.saveBtn_CPF);
        fab = view.findViewById(R.id.fab_CPF);
        profileImageView = view.findViewById(R.id.profile_image_CPF);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (SharedPrefManagerClient.getInstance(context).isLoggedIn()) {
            email.setText(SharedPrefManagerClient.getInstance(context).getClient().getEmail());
            name.setText(SharedPrefManagerClient.getInstance(context).getClient().getName());
            address.setText(SharedPrefManagerClient.getInstance(context).getClient().getAddress());
            mobile.setText(SharedPrefManagerClient.getInstance(context).getClient().getNumber());
            GetAllProfileData();
        }

        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
            }
        } catch (Exception e) {
            Log.e("Client Profile Fragment",e.getMessage());
            e.printStackTrace();
        }

        gender_items = new ArrayList<>();
        gender_items = Arrays.asList(getResources().getStringArray(R.array.gender_array));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, gender_items);
        gender.setAdapter(adapter);



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetCheck internetCheck = new InternetCheck(context);
                if (!internetCheck.InternetConnection()) {
                    AlertDisplayer alertDisplayerq = new AlertDisplayer(getString(R.string.error), getString(R.string.no_internet_message), context);
                    alertDisplayerq.alertDisplayer();

                } else {
                    Log.wtf("IMAGE", encodeImage);
                    SaveProfileData();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Choose application"), REQCODE_CP);
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateView.setText(sdf.format(calendar.getTime()));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode == REQCODE_CP && resultCode == RESULT_OK && data != null) {

                    Uri uri;
                    uri = data.getData();
                    String filePath= PathUtil.getPath(context,uri);

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(filePath));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    profileImageView.setImageBitmap(bitmap);
                    byte[] b = byteArrayOutputStream.toByteArray();

                    encodeImage = Base64.encodeToString(b, Base64.DEFAULT);



            } else {
                Toast.makeText(getActivity(), "You haven't picked Image"+data, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            Log.wtf("ASDFGH----->", e.getMessage());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SaveProfileData() {

        final String name_ = name.getText().toString().trim();
        final String email_ = email.getText().toString().trim();
        final String mobile_ = mobile.getText().toString().trim();
        final String living_address = address.getText().toString().trim();
        final String b_name = B_name.getText().toString().trim();
        final String b_activity_ = B_Activity.getText().toString().trim();
        final String b_address = B_address.getText().toString().trim();
        final String b_phone = B_phone.getText().toString().trim();
        final String dob_ = dateView.getText().toString().trim();
        final String gender_ = gender.getSelectedItem().toString().trim();

        class UserUpdate extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                saveBtn.setActivated(true);
                saveBtn.setClickable(false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                saveBtn.setActivated(false);
                saveBtn.setClickable(true);
                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        SharedPrefManagerClient.getInstance(context).logoutWithNoIntent();
                        //getting the user from the response
                        JSONObject clientJson = obj.getJSONObject("user");

                        //creating a new user object
                        Client client = new Client(
                                clientJson.getInt("id"),
                                clientJson.getString("name"),
                                clientJson.getString("email"),
                                clientJson.getString("number"),
                                clientJson.getString("living_address"),
                                clientJson.getInt("status")
                        );

                        SharedPrefManagerClient.getInstance(context).clientLogin(client);

                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR UPDATE", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                String client_id = String.valueOf(SharedPrefManagerClient.getInstance(context).getClient().getId());

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("client_id", client_id);
                params.put("name", name_);
                params.put("email", email_);
                params.put("number", mobile_);
                params.put("address", living_address);
                params.put("business_name", b_name);
                params.put("business_activity", b_activity_);
                params.put("business_phone", b_phone);
                params.put("business_address", b_address);
                params.put("gender", gender_);
                params.put("dob", dob_);
                if (encodeImage != null) {
                    params.put("image", encodeImage);
                }

                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_CHANGE_CLIENT_PROFILE, params);
            }
        }
        UserUpdate ur = new UserUpdate();
        ur.execute();

    }

    private void GetAllProfileData() {


        class GetProfileData extends AsyncTask<Void, Void, String> {


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
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject clientJson = obj.getJSONObject("client");

                        //creating a new user object


                        if (clientJson.has("gender") && clientJson.getString("gender") != null) {
                            if (clientJson.getString("gender").equalsIgnoreCase("Male")) {
                                gender.setSelection(1);
                            }
                            if (clientJson.getString("gender").equalsIgnoreCase("Female")) {
                                gender.setSelection(2);
                            }
                        }
                        if (clientJson.has("dob") && !clientJson.getString("dob").equalsIgnoreCase("null") ) {
                            dateView.setText(clientJson.getString("dob"));
                        }
                        if (clientJson.has("business_name")&& !clientJson.getString("business_name").equalsIgnoreCase("null")) {
                            B_name.setText(clientJson.getString("business_name"));
                        }
                        if (clientJson.has("business_activity")&& !clientJson.getString("business_activity").equalsIgnoreCase("null")) {
                            B_Activity.setText(clientJson.getString("business_activity"));
                        }
                        if (clientJson.has("business_phone")&& !clientJson.getString("business_phone").equalsIgnoreCase("null")) {
                            B_phone.setText(clientJson.getString("business_phone"));
                        }
                        if (clientJson.has("business_address")&& !clientJson.getString("business_address").equalsIgnoreCase("null")) {
                            B_address.setText(clientJson.getString("business_address"));
                        }
                        if (clientJson.has("image")&& !clientJson.getString("image").equalsIgnoreCase("null")) {
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_profile)
                                    .error(R.drawable.ic_profile).onlyRetrieveFromCache(false);
                            Glide.with(context).load(Apis.BASE_URL+clientJson.getString("image")).apply(options).into(profileImageView);
                        }


                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR UPDATE", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                String client_id = String.valueOf(SharedPrefManagerClient.getInstance(context).getClient().getId());

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("client_id", client_id);

                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_GET_CLIENT_PROFILE, params);
            }
        }
        GetProfileData gpd = new GetProfileData();
        gpd.execute();

    }


}
