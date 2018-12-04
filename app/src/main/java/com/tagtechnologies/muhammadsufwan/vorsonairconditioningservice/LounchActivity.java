package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.TechnicianModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.ClientDashBoardActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.LoginClientActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.SharedPrefManagerClient;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.extrass.ConnectivityReceiver;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.extrass.MyApplication;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.productsAndSupplies.ProductsAndCategoryActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.SharedPrefManagerTechnician;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.TechnicianDashboardActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.AlertDisplayer;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.InternetCheck;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.TicketActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class LounchActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private TextView forUser, forTechnician;
    private Button formBtn, suppliesBtn;
    private Context mContext;
    private RelativeLayout v;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounch);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mContext = LounchActivity.this;

//        Binding layout items

        forUser = findViewById(R.id.lounch_client_login);
        forTechnician = findViewById(R.id.lounch_technician_login);
        formBtn = findViewById(R.id.lounch_form_button);
        suppliesBtn = findViewById(R.id.lounch_supplies_button);
        logo = findViewById(R.id.launchActivityLogo);
        v = findViewById(R.id.parent_layout_louncher);

        //logo.animate().rotation(360).setDuration(1500);

        //logo.startAnimation(mContext.getResources().getAnimation(android.R.anim.fade_in));

        forUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InternetCheck internetCheck = new InternetCheck(LounchActivity.this);
                if (!internetCheck.InternetConnection()) {
                    AlertDisplayer alertDisplayerq = new AlertDisplayer(getString(R.string.error), getString(R.string.no_internet_message), LounchActivity.this);
                    alertDisplayerq.alertDisplayer();

                } else {
                    startActivity(new Intent(LounchActivity.this, ClientDashBoardActivity.class));
                }
            }
        });

//        forUser.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent.ShortcutIconResource icon =
//                        Intent.ShortcutIconResource.fromContext(mContext, R.drawable.holygrailz_uk_profile_ui);
//
//                Intent intent = new Intent();
//
//                Intent launchIntent = new Intent(mContext,ClientDashBoardActivity.class);
//
//                intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
//                intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Vorson Client ShortCut");
//                intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//
//                setResult(RESULT_OK, intent);
//
//                Toast.makeText(mContext, "Short Cut Created", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        formBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LounchActivity.this, TicketActivity.class));
            }
        });

        forTechnician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPrefManagerTechnician.getInstance(mContext).isLoggedIn()) {
                    startActivity(new Intent(LounchActivity.this, TechnicianDashboardActivity.class));
                    return;
                }

                InternetCheck internetCheck = new InternetCheck(LounchActivity.this);
                if (!internetCheck.InternetConnection()) {
                    AlertDisplayer alertDisplayerq = new AlertDisplayer(getString(R.string.error), getString(R.string.no_internet_message), LounchActivity.this);
                    alertDisplayerq.alertDisplayer();

                } else {

                    loginTechnician();
                }
            }
        });

        suppliesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LounchActivity.this, ProductsAndCategoryActivity.class));
            }
        });

    }

    public void loginTechnician() {

        final EditText usernameE, passwordE;
        final Button login;

        AlertDialog.Builder myDialog = new AlertDialog.Builder(LounchActivity.this);
        View myView = getLayoutInflater().inflate(R.layout.technician_login_window, null);
        myDialog.setView(myView);
        usernameE = (EditText) myView.findViewById(R.id.loginTechniEmial);
        passwordE = (EditText) myView.findViewById(R.id.loginTechniPassword);
        login = (Button) myView.findViewById(R.id.login_techni_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String username = usernameE.getText().toString();
                final String password = passwordE.getText().toString();

                //validating inputs
                if (TextUtils.isEmpty(username)) {
                    usernameE.setError("Enter a valid User name");
                    usernameE.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordE.setError("Please enter your password");
                    passwordE.requestFocus();
                    return;
                }

                //if everything is fine

                class UserLogin extends AsyncTask<Void, Void, String> {


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

                                //creating a new user

                                TechnicianModule technicianModule = new TechnicianModule(

                                        clientJson.getInt("id"),
                                        clientJson.getString("username"),
                                        clientJson.getString("email"),
                                        clientJson.getString("address"),
                                        clientJson.getString("mobile_number"),
                                        clientJson.getString("dob")
                                );

                                if (clientJson.has("image")) {
                                    SharedPrefManagerTechnician.getInstance(LounchActivity.this).clearImage();
                                    SharedPrefManagerTechnician.getInstance(LounchActivity.this).setImage(Apis.BASE_URL + clientJson.getString("image"));
                                }

                                //storing the user in shared preferences

                                SharedPrefManagerTechnician.getInstance(getApplicationContext()).clientLogin(technicianModule);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), TechnicianDashboardActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                        String token = SharedPrefManagerClient.getInstance(LounchActivity.this).getToken();
                        //creating request parameters
                        HashMap<String, String> params = new HashMap<>();
                        params.put("token", token);
                        params.put("username", username);
                        params.put("password", password);
                        //returing the response
                        return requestHandler.sendPostRequest(Apis.TECHNICIAN_LOGIN, params);
                    }
                }

                UserLogin ul = new UserLogin();
                ul.execute();

            }
        });


        AlertDialog dialog = myDialog.create();
        dialog.show();

        //first getting the values

    }

    private void addShortcut() {

        Intent shortcutIntent = new Intent(getApplicationContext(),ClientDashBoardActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, /*this.getResources().getString(R.string.app_name)*/"Vorson Client Dashboard");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                R.drawable.holygrailz_uk_profile_ui));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }

}
