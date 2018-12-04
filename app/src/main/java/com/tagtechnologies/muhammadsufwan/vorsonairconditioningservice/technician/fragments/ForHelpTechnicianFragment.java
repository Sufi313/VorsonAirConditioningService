package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.SharedPrefManagerTechnician;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.TechnicianDashboardActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.ImageListAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class ForHelpTechnicianFragment extends Fragment {

    private Context context;
    private RelativeLayout relative_view;
    private ImageListAdapter adapter;
    private TextView clientName, complaintNumber, message;
    private Button submit, addImages;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    public static final int REQCODE = 100;
    public static final String TAG = ForHelpTechnicianFragment.class.getSimpleName();
    private JSONObject jsonObject;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    String imageURI;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_for_help_technician, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clientName = view.findViewById(R.id.clientNameForHelpTechnicianFragment);
        complaintNumber = view.findViewById(R.id.subjectForHelpTechnicianFragment);
        message = view.findViewById(R.id.messageForHelpTechnicianFragment);
        submit = view.findViewById(R.id.submitBtnForHelpTechnicianFragment);
        addImages = view.findViewById(R.id.addImageBtnForHelpTechnicianFragment);
        recyclerView = view.findViewById(R.id.recyclerViewForHelpTechnicianFragment);
        progressBar = view.findViewById(R.id.progressBarForHelpTechnicianFragment);

        context = getActivity();

        encodedImageList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                addImages.setClickable(false);
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,e.getMessage());
        }

        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Choose application"), REQCODE);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitTicket();
            }
        });

    }

    private void SubmitTicket() {
        //first getting the values
        final String fullName = clientName.getText().toString();
        final String comp_number = complaintNumber.getText().toString();
        final String _message = message.getText().toString();
        final String stringDate = DateFormat.getDateInstance().format(new Date());

        //validating inputs
        if (TextUtils.isEmpty(fullName)) {
            clientName.setError("Please enter client name");
            clientName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(_message)) {
            message.setError("Enter message at least some words");
            message.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(comp_number)) {
            complaintNumber.setError("Enter complaint number");
            complaintNumber.requestFocus();
            return;
        }
        //if everything is fine

        class Submit extends AsyncTask<Void, Void, String> {

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

                        Toast.makeText(getContext(), obj.getString("result"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(),TechnicianDashboardActivity.class));

                    } else {
                        Toast.makeText(getContext(), obj.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR TECH HELP SUBMIT", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                final String client_id = String.valueOf(SharedPrefManagerTechnician.getInstance(context).getTechnician().getId());
                final String techni_name = SharedPrefManagerTechnician.getInstance(context).getTechnician().getName();

                if (encodedImageList.isEmpty()) {
                    params.put("technician_id", client_id);
                    params.put("technician_name", techni_name);
                    params.put("client_name", fullName);
                    params.put("complaint_no", comp_number);
                    params.put("help_message", _message);
                    params.put("created_at", stringDate);
                }

//                jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    for (String encoded : encodedImageList) {
                        jsonObject = new JSONObject();
                        jsonObject.put("image", encoded);
                        jsonArray.put(jsonObject);
                    }
                } catch (JSONException e) {
                    Log.wtf("JSONException",e.getMessage());
                }

                params.put("technician_id", client_id);
                params.put("technician_name", techni_name);
                params.put("client_name", fullName);
                params.put("complaint_no", comp_number);
                params.put("help_message", _message);
                params.put("created_at", stringDate);
                params.put("images", jsonArray.toString());
                return requestHandler.sendPostRequest(Apis.SET_TECH_HELP_URL, params);
                //returing the response
            }
        }

        Submit s = new Submit();
        s.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            // When an Image is picked
            if (requestCode == REQCODE && resultCode == RESULT_OK
                    && data != null) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesUriList = new ArrayList<>();
                encodedImageList.clear();

              /*  if(data.getData()!=null){

                    Uri myImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = context.getContentResolver().query(myImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageURI  = cursor.getString(columnIndex);
                    cursor.close();


                }else {*/
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        // Get the cursor
                        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageURI = cursor.getString(columnIndex);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        encodedImageList.add(encodedImage);
                        cursor.close();
                    }
                    adapter = new ImageListAdapter(getActivity(), mArrayUri);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                //}
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


}
