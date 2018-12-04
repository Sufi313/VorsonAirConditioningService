package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;
/*******************************************************/
/* Created by Muhammad.Sufwan on 5/14/2018 user email ${EMAIL};. */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

import java.util.List;

/*******************************************************/
public class TechniTimeListAdapter extends RecyclerView.Adapter<TechniTimeListAdapter.RecyclerViewHolder> {

    private Context mCtx;
    List<GetTimeModule> timeList;

    public TechniTimeListAdapter(Context mCtx, List<GetTimeModule> timeList) {
        this.mCtx = mCtx;
        this.timeList = timeList;
    }


    @Override
    public TechniTimeListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_job_list_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechniTimeListAdapter.RecyclerViewHolder holder, int position) {
            GetTimeModule module = timeList.get(position);

            holder.textView1.setText(String.valueOf(position+1));

            holder.textView2.setText(module.getClientName());

            holder.textView3.setText(module.getCreated_at());

            holder.textView4.setText(module.getWorking_time());

    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1,textView2,textView3,textView4;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.assignJobList_complaintID);
            textView2 = itemView.findViewById(R.id.assignJobList_clientName);
            textView3 = itemView.findViewById(R.id.assignJobList_serviceType);
            textView4 = itemView.findViewById(R.id.assignJobList_servicePriority);

        }
    }



}
