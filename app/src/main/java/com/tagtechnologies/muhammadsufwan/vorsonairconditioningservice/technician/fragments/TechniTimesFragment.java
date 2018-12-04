package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.SharedPrefManagerTechnician;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.GetTimeModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.TechniTimeListAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class TechniTimesFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context mContext;
    private List<GetTimeModule> list;
    private int totalTime;
    private int todayTotalTime;
    private int MonthTotalTime;
    private int yearTotalTime;
    private TextView tvTotalTime,tvTodayTime,tvMonthTime,tvYearTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_techni_times, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();
        mContext = getActivity();
        tvTotalTime = view.findViewById(R.id.totalTime_timeList);
        tvMonthTime = view.findViewById(R.id.monthTotalTime_timeList);
        tvTodayTime = view.findViewById(R.id.totalTimeToday_timeList);
        tvYearTime = view.findViewById(R.id.totalYearTime_timeList);
        recyclerView = view.findViewById(R.id.techni_fragment_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        getTime gt = new getTime(mContext, view);
        gt.execute();

    }

    private class getTime extends AsyncTask<Void, Void, String> {
        private ProgressBar progressBar;
        private Context mContext;
        private View rootView;


        private getTime(Context context, View rootView) {
            this.mContext = context;
            this.rootView = rootView;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = rootView.findViewById(R.id.techni_fragment_progressBar);
            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            list.clear();
            try {

                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(mContext, object.getString("result"), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = object.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        object = jsonArray.getJSONObject(i);
                        list.add(new GetTimeModule(
                                object.getInt("id"),
                                object.getString("client_id_name"),
                                object.getString("created_at"),
                                object.getString("time_in"),
                                object.getString("time_out"),
                                object.getString("working_time")
                        ));
                    }
                }

                int minutes = 0;
                for (int j = 0;j < list.size(); j++){
                    minutes = Integer.parseInt( list.get(j).getWorking_time());
                     totalTime = totalTime+minutes;
                }

                int todayMinutes = 0;
                String checkTodayDate = DateFormat.getDateInstance().format(new Date());


                for(GetTimeModule d : list){
                    if(d.getCreated_at() != null && d.getCreated_at().contains(checkTodayDate)){
                        todayMinutes = Integer.parseInt( d.getWorking_time());
                        todayTotalTime = todayTotalTime+todayMinutes;
                    }
                }

                int monthMinutes = 0;

                String alldatesofMonth = DateFormat.getDateInstance().format(new Date());;
                String array3[] = alldatesofMonth.split(" ");

                for(GetTimeModule d : list){
                    if(d.getCreated_at() != null && d.getCreated_at().contains(array3[0]) && d.getCreated_at().contains(array3[2])){
                        monthMinutes = Integer.parseInt( d.getWorking_time());
                        MonthTotalTime = MonthTotalTime+monthMinutes;
                    }

                }

                int yearMinutes = 0;

                for(GetTimeModule d : list){
                    if(d.getCreated_at() != null && d.getCreated_at().contains(array3[2])){
                        yearMinutes = Integer.parseInt( d.getWorking_time());
                        yearTotalTime = yearTotalTime+yearMinutes;
                    }

                }

                String startTime = "00:00";

                int th = todayTotalTime / 60 + Integer.parseInt(startTime.substring(0,1));
                int tm = todayTotalTime % 60 + Integer.parseInt(startTime.substring(3,4));
                String newTodayTime = th+"h:"+tm+"m";

                tvTodayTime.setText(newTodayTime);

                int mh = MonthTotalTime / 60 + Integer.parseInt(startTime.substring(0,1));
                int mm = MonthTotalTime % 60 + Integer.parseInt(startTime.substring(3,4));
                String newMonthTime = mh+"h:"+mm+"m";

                tvMonthTime.setText(newMonthTime);

                int yh = yearTotalTime / 60 + Integer.parseInt(startTime.substring(0,1));
                int ym = yearTotalTime % 60 + Integer.parseInt(startTime.substring(3,4));
                String newYearTime = yh+"h:"+ym+"m";

                tvYearTime.setText(newYearTime);

                int h = totalTime / 60 + Integer.parseInt(startTime.substring(0,1));
                int m = totalTime % 60 + Integer.parseInt(startTime.substring(3,4));
                String newTimeTotal = h+"h:"+m+"m";

                tvTotalTime.setText(newTimeTotal);




                TechniTimeListAdapter adapter = new TechniTimeListAdapter(mContext,list);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            String id = String.valueOf(SharedPrefManagerTechnician.getInstance(getActivity()).getTechnician().getId());
            params.put("technician_id", id);

            return requestHandler.sendPostRequest(Apis.TECHNI_GET_TIME_URL, params);
        }
    }

}
