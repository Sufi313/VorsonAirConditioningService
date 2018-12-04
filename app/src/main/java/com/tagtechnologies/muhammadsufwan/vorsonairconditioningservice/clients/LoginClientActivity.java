package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.Client;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.dbHelper.VorsonDbHelper;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.AlertDisplayer;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.PasswordValidator;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginClientActivity extends AppCompatActivity {

    private TextView forgetPassword, register;
    private Button loginBtn;
    private EditText editTextEmail, editTextPassword;
    private View myView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);

//        Type Casting XML Elements
        loginBtn = findViewById(R.id.login_client_btn);
        forgetPassword = findViewById(R.id.tv_forget_password);
        editTextEmail = findViewById(R.id.loginEmial);
        editTextPassword = findViewById(R.id.loginPassword);
        register = findViewById(R.id.tv_register);
//        Type Casting XML Elements

        myView = getLayoutInflater().inflate(R.layout.user_registration, null);

        forgetPassword.setMovementMethod(LinkMovementMethod.getInstance());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void RegisterUser() {

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(LoginClientActivity.this);
        myDialog.setView(myView);

        Button BtnRegister = myView.findViewById(R.id.reg_client_btn);

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        AlertDialog dialog = myDialog.create();
        dialog.show();

    }

    private void clientLogin() {
        //first getting the values
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = findViewById(R.id.progressBarLogin);
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

                        if (obj.getInt("status") == 0) {
                            AlertDisplayer alertDisplayer = new AlertDisplayer("Error", "There is no account found on this Number OR Email", LoginClientActivity.this);
                            alertDisplayer.alertDisplayer2();

                        }
                        if (obj.getInt("status") == 1) {

                            AlertDisplayer alertDisplayer = new AlertDisplayer("Error", "Password not matched!", LoginClientActivity.this);
                            alertDisplayer.alertDisplayer2();

                        }
                        if (obj.getInt("status") == 2) {

                            Toast.makeText(LoginClientActivity.this, "Device Token Not Updated", Toast.LENGTH_SHORT).show();
                            //getting the user from the response
                            JSONObject clientJson = obj.getJSONObject("user");

                            //creating a new user object
                            Client client = new Client(
                                    clientJson.getInt("id"),
                                    clientJson.getString("f_name") + " " + clientJson.getString("l_name"),
                                    clientJson.getString("email"),
                                    clientJson.getString("phone"),
                                    clientJson.getString("address"),
                                    clientJson.getInt("status")
                            );

                            //starting the profile activity

                            SharedPrefManagerClient.getInstance(LoginClientActivity.this).clientLogin(client);
                            startActivity(new Intent(LoginClientActivity.this, ClientDashBoardActivity.class));
                            VorsonDbHelper vorsonDbHelper = new VorsonDbHelper(LoginClientActivity.this);
                            long result =  vorsonDbHelper.insert(client);
                            if (result>0){
                                Toast.makeText(LoginClientActivity.this, "DATA ADDED", Toast.LENGTH_LONG).show();
                            }
                            finish();

                        }
                        if (obj.getInt("status") == 3) {

                            //getting the user from the response
                            JSONObject clientJson = obj.getJSONObject("user");

                            //creating a new user object
                            Client client = new Client(
                                    clientJson.getInt("id"),
                                    clientJson.getString("f_name") + " " + clientJson.getString("l_name"),
                                    clientJson.getString("email"),
                                    clientJson.getString("phone"),
                                    clientJson.getString("address"),
                                    clientJson.getInt("status")
                            );


                            //starting the profile activity
                            SharedPrefManagerClient.getInstance(LoginClientActivity.this).clientLogin(client);
                            startActivity(new Intent(LoginClientActivity.this, ClientDashBoardActivity.class));
                            finish();


                        }

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                String token = SharedPrefManagerClient.getInstance(LoginClientActivity.this).getToken();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                if (!token.isEmpty()) {
                    params.put("token", token);
                    Log.d("Token", token);
                }
                params.put("username", email);
                params.put("password", password);
                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_CLIENT_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    private void Register() {

        EditText userNameReg = (EditText) myView.findViewById(R.id.regName);
        EditText userEmailReg = (EditText) myView.findViewById(R.id.regEmail);
        EditText userMobileReg = (EditText) myView.findViewById(R.id.regMobile);
        EditText userAddressReg = (EditText) myView.findViewById(R.id.regAddress);
        EditText userPasswordReg = (EditText) myView.findViewById(R.id.regPassword);
        EditText userConfirmPasswordReg = (EditText) myView.findViewById(R.id.regPasswordConfirm);

        final String UserName = userNameReg.getText().toString();
        final String UserEmail = userEmailReg.getText().toString();
        final String UserNumber = userMobileReg.getText().toString();
        final String UserAddress = userAddressReg.getText().toString();
        final String UserPassword = userPasswordReg.getText().toString();
        final String UserConfirmPassword = userConfirmPasswordReg.getText().toString();
        PasswordValidator passwordValidator = new PasswordValidator();


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()) {
            userEmailReg.setError("Enter a valid email");
            userEmailReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(UserPassword)) {
            userPasswordReg.setError("Please enter your password");
            userPasswordReg.requestFocus();
            return;
        }

        if (!passwordValidator.validate(UserPassword)) {
            userPasswordReg.setError("Please use both alphabates and numeric characters ");
            userPasswordReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(UserConfirmPassword)) {
            userConfirmPasswordReg.setError("Please re enter your password");
            userConfirmPasswordReg.requestFocus();
            return;
        }

        if (!UserPassword.equals(UserConfirmPassword)) {
            userConfirmPasswordReg.setError("Password not match");
            userConfirmPasswordReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(UserName)) {
            userNameReg.setError("Please enter your full name");
            userNameReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(UserAddress)) {
            userAddressReg.setError("Please re enter your address");
            userAddressReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(UserNumber)) {
            userMobileReg.setError("Please re enter your mobile number");
            userMobileReg.requestFocus();
            return;
        }


        class UserRegister extends AsyncTask<Void, Void, String> {


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
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject clientJson = obj.getJSONObject("user");

                        Client client = new Client(
                                clientJson.getInt("id"),
                                clientJson.getString("name"),
                                clientJson.getString("email"),
                                clientJson.getString("number"),
                                clientJson.getString("address"),
                                clientJson.getInt("status")
                        );


                        //storing the user in shared preferences
                        SharedPrefManagerClient.getInstance(getApplicationContext()).clientLogin(client);
                        finish();
                        startActivity(new Intent(LoginClientActivity.this, ClientDashBoardActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR REGISTER", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //String token = SharedPrefManagerClient.getInstance(LoginClientActivity.this).getToken();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                //params.put("token", token);
                params.put("name", UserName);
                params.put("email", UserEmail);
                params.put("number", UserNumber);
                params.put("address", UserAddress);
                params.put("password", UserConfirmPassword);

                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_CLIENT_REGISTER, params);
            }
        }
        UserRegister ur = new UserRegister();
        ur.execute();
    }


}
