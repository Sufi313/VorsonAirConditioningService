package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.util.HashMap;

public class GetData extends AsyncTask<Void, Void, String> {

    private RequestHandler requestHandler = new RequestHandler();
    private String url;
    private HashMap<String, String> params;
    private GetDataListener asyncResponse = null;
    private int requestCode;


    public GetData(String url, HashMap<String, String> params, int requestCode, Context context) {
        this.url  = url;
        this.params = params;
        this.requestCode = requestCode;
        asyncResponse = (GetDataListener) context;
    }

    @Override
    protected String doInBackground(Void... voids) {

        return requestHandler.sendPostRequest(url, params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (asyncResponse != null) {
            //post result with given requestCode
            this.asyncResponse.getDownloadData(s, requestCode);
            Log.d("GET DATA",s);
        }
    }

    public interface GetDataListener {

        void getDownloadData(String result,int request);

    }

}
