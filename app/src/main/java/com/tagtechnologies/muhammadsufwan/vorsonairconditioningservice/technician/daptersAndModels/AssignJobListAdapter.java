package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.ViewAssignJobActivity;

import java.util.ArrayList;
import java.util.List;


public class AssignJobListAdapter extends RecyclerView.Adapter<AssignJobListAdapter.RecyclerViewHolder> implements Filterable {

    private Context mCtx;
    private List<AssignJobModule> assignJobList;
    private List<AssignJobModule> assignJobListFiltered;

    public AssignJobListAdapter(Context mCtx, List<AssignJobModule> assignJobList) {
        this.mCtx = mCtx;
        this.assignJobList = assignJobList;
        this.assignJobListFiltered = assignJobList;
    }


    @NonNull
    @Override
    public AssignJobListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_job_list_layout, parent, false);
            return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {

        final AssignJobModule module = assignJobListFiltered.get(position);

        holder.tvComplaintId.setText(String.valueOf(module.getComplaintNumber()));

        holder.tvClientName.setText(module.getClientName());

        holder.tvServiceType.setText(module.getServiceType());

        holder.tvServicePriority.setText(module.getServicePriority());

        holder.onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ViewAssignJobActivity.class);
                intent.putExtra("id",module.getId());
                intent.putExtra("complain_id",module.getComplaintNumber());
                intent.putExtra("client_name",module.getClientName());
                intent.putExtra("service_type",module.getServiceType());
                intent.putExtra("service_priority",module.getServicePriority());
                intent.putExtra("phone_number",module.getPhoneNumber());
                intent.putExtra("message",module.getMessage());
                intent.putExtra("address",module.getAddress());
                intent.putExtra("status",module.getStatus());
                mCtx.startActivity(intent);
            }
        });

        }


    @Override
    public int getItemCount() {

            return assignJobListFiltered.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvComplaintId, tvClientName, tvServiceType, tvServicePriority;
        LinearLayout onClick;
        private RecyclerViewHolder(View itemView) {
            super(itemView);

            onClick = itemView.findViewById(R.id.assignJobList);
            tvComplaintId = itemView.findViewById(R.id.assignJobList_complaintID);
            tvClientName = itemView.findViewById(R.id.assignJobList_clientName);
            tvServiceType = itemView.findViewById(R.id.assignJobList_serviceType);
            tvServicePriority = itemView.findViewById(R.id.assignJobList_servicePriority);

        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    assignJobListFiltered = assignJobList;
                } else {
                    List<AssignJobModule> filteredList = new ArrayList<>();
                    for (AssignJobModule row : assignJobList) {


                        if (row.getServiceType().toLowerCase().contains(charString.toLowerCase()) || row.getClientName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    assignJobListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = assignJobListFiltered;
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                assignJobListFiltered = (ArrayList<AssignJobModule>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
