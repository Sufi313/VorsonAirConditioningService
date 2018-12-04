package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments;
/*******************************************************/
/* Created by muhammad.sufwan on 5/24/2018 user email ${EMAIL};. */

import android.Manifest;
import android.app.DatePickerDialog;

import android.content.Context;


import android.content.Intent;


import android.content.pm.PackageManager;

import android.graphics.Bitmap;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TechnicianModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.SharedPrefManagerTechnician;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.AlertDisplayer;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.InternetCheck;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments.ForHelpTechnicianFragment.REQCODE;

/*******************************************************/
public class ProfileFragment extends Fragment {


    private Calendar calendar;
    private EditText dateView, address, email, mobile;
    private int year, month, day;
    private ImageView profileImageView;
    private FloatingActionButton fab;
    private Button saveBtn;
    private Context context;

    private String encodeImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_techni_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        dateView = view.findViewById(R.id.etdob_TPF);
        address = view.findViewById(R.id.etAddress_TPF);
        email = view.findViewById(R.id.etEmail_TPF);
        mobile = view.findViewById(R.id.etMobile_TPF);
        saveBtn = view.findViewById(R.id.saveBtn_TPF);
        calendar = Calendar.getInstance();
        fab = view.findViewById(R.id.fab_TPF);
        profileImageView = view.findViewById(R.id.profile_image_TPF);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (SharedPrefManagerTechnician.getInstance(context).isLoggedIn()) {
            dateView.setText(SharedPrefManagerTechnician.getInstance(context).getTechnician().getDob());
            email.setText(SharedPrefManagerTechnician.getInstance(context).getTechnician().getEmail());
            address.setText(SharedPrefManagerTechnician.getInstance(context).getTechnician().getAddress());
            mobile.setText(SharedPrefManagerTechnician.getInstance(context).getTechnician().getMobile());
        }

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

        if (SharedPrefManagerTechnician.getInstance(context).isImageIn()) {

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile);
            Glide.with(context).load(SharedPrefManagerTechnician.getInstance(context).getImage()).apply(options).into(profileImageView);

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Choose application"), REQCODE);
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
            if (requestCode == REQCODE && resultCode == RESULT_OK
                    && data != null) {

                if (data.getClipData() != null) {
                    Uri uri;
                    uri = data.getData();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    profileImageView.setImageBitmap(bitmap);
                    byte[] b = byteArrayOutputStream.toByteArray();

                    encodeImage = Base64.encodeToString(b, Base64.DEFAULT);

                }

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            Log.wtf("ASDFGH----->", e.getMessage());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SaveProfileData() {

        final String email_ = email.getText().toString().trim();
        final String dob_ = dateView.getText().toString().trim();
        final String address_ = address.getText().toString().trim();
        final String mobile_ = mobile.getText().toString().trim();

        class UserUpdate extends AsyncTask<Void, Void, String> {


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

                        SharedPrefManagerTechnician.getInstance(context).logoutWithNoIntent();
                        //getting the user from the response
                        JSONObject clientJson = obj.getJSONObject("user");


                        TechnicianModule technicianModule = new TechnicianModule(

                                clientJson.getInt("id"),
                                clientJson.getString("username"),
                                clientJson.getString("email"),
                                clientJson.getString("address"),
                                clientJson.getString("mobile_number"),
                                clientJson.getString("dob")
                        );

                        if (clientJson.has("image")) {
                            SharedPrefManagerTechnician.getInstance(context).clearImage();
                            SharedPrefManagerTechnician.getInstance(context).setImage(Apis.BASE_URL + clientJson.getString("image"));
                        }

                        //storing the user in shared preferences
                        SharedPrefManagerTechnician.getInstance(context).clientLogin(technicianModule);


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

                String technician_id = String.valueOf(SharedPrefManagerTechnician.getInstance(context).getTechnician().getId());

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("technician_id", technician_id);
                params.put("email", email_);
                params.put("dob", dob_);
                params.put("number", mobile_);
                params.put("address", address_);
                if (encodeImage != null) {
                    params.put("image", encodeImage);
                }

                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_CHANGE_TECH_PROFILE, params);
            }
        }
        UserUpdate ur = new UserUpdate();
        ur.execute();

    }
}
