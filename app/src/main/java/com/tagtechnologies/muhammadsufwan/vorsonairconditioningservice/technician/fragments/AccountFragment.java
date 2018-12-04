package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments;
/*******************************************************/
/* Created by muhammad.sufwan on 5/24/2018 user email ${EMAIL};. */

/*******************************************************/
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.LounchActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.LoginClientActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.SharedPrefManagerClient;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.SharedPrefManagerTechnician;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.AlertDisplayer;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.InternetCheck;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */

public class AccountFragment extends Fragment {

    private EditText userName,oldPassword,newPassword,confirmPassword;
    private Button save;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        userName = view.findViewById(R.id.etUserName_TAF);
        oldPassword = view.findViewById(R.id.etOldPassword_TAF);
        newPassword = view.findViewById(R.id.etPassword_TAF);
        confirmPassword = view.findViewById(R.id.etConfirmPassword_TAF);
        save = view.findViewById(R.id.changePasswordbtn_TAF);

        userName.setText(SharedPrefManagerTechnician.getInstance(context).getTechnician().getName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetCheck internetCheck = new InternetCheck(context);
                if (!internetCheck.InternetConnection()){
                    AlertDisplayer alertDisplayerq = new AlertDisplayer(getString(R.string.error), getString(R.string.no_internet_message),context);
                    alertDisplayerq.alertDisplayer();

                }else{
                    SaveChanges();
                }
            }
        });
    }

    public void SaveChanges(){
        final String oldPassword_ = oldPassword.getText().toString().trim();
        final String newPassword_ = newPassword.getText().toString().trim();
        String confirmPassword_ = confirmPassword.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(oldPassword_)) {
            oldPassword.setError("Enter old password");
            oldPassword.requestFocus();
            return;
        }if (TextUtils.isEmpty(newPassword_)) {
            newPassword.setError("Enter new password");
            newPassword.requestFocus();
            return;
        }if (TextUtils.isEmpty(confirmPassword_)) {
            confirmPassword.setError("Enter confirm password");
            confirmPassword.requestFocus();
            return;
        }
        if (!newPassword_.equals(confirmPassword_)) {
            confirmPassword.setError("Password not match");
            confirmPassword.requestFocus();
            return;
        }


        class ChangePassword extends AsyncTask<Void, Void, String>{
            private ProgressDialog dialog = new ProgressDialog(context);
            @Override
            protected void onPreExecute() {
                dialog.setMessage("Please wait...");
                dialog.show();
                super.onPreExecute();


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(context, obj.getString("result"), Toast.LENGTH_SHORT).show();

                        SharedPrefManagerTechnician.getInstance(context).logout();
                        startActivity(new Intent(context, LounchActivity.class));
                        Objects.requireNonNull(getActivity()).finish();

                    } else {
                        Toast.makeText(context, obj.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR CHANGE PASSWORD", e.getMessage());
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
                params.put("old_password", oldPassword_);
                params.put("new_password", newPassword_);
                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_CHANGE_TECH_PASS, params);
            }
        }

        ChangePassword cp = new ChangePassword();
        cp.execute();

    }
}
