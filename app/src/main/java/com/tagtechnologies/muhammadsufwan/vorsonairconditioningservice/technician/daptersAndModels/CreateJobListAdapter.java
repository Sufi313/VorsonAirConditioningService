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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.details.InvoiceDetailActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.ViewAssignJobActivity;

import java.util.ArrayList;
import java.util.List;


public class CreateJobListAdapter extends RecyclerView.Adapter<CreateJobListAdapter.RecyclerViewHolder> implements Filterable {

    private Context mCtx;
    private List<JobFormModel> createJobList;
    private List<JobFormModel> createJobListFiltered;

    public CreateJobListAdapter(Context mCtx, List<JobFormModel> createJobList) {
        this.mCtx = mCtx;
        this.createJobList = createJobList;
        this.createJobListFiltered = createJobList;
    }

    @NonNull
    @Override
    public CreateJobListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_form_list_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {

        final JobFormModel module = createJobListFiltered.get(position);

        holder.tvSerialNumber.setText(String.valueOf(position + 1));

        holder.tvClientName.setText(module.getClient_id_name());

        holder.tvJobId.setText(String.valueOf(module.getId()));

        holder.tvCreated_at.setText(module.getCreated_at());

        if (module.getStatus() == 0) {
            holder.invoiceCheck.setBackgroundResource(R.drawable.ic_un_check);
        }
        if (module.getStatus() == 1) {
            holder.invoiceCheck.setBackgroundResource(R.drawable.ic_check);
        }

        holder.onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ViewAssignJobActivity.class);
                intent.putExtra("id", module.getId());
                intent.putExtra("created_at", module.getCreated_at());
                intent.putExtra("client_name", module.getClient_id_name());
                intent.putExtra("address", module.getStatus());

                mCtx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return createJobListFiltered.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvSerialNumber, tvJobId, tvClientName, tvCreated_at;
        RelativeLayout onClick;
        ImageView invoiceCheck;

        private RecyclerViewHolder(View itemView) {
            super(itemView);

            onClick = itemView.findViewById(R.id.JobFormList_layout);
            tvSerialNumber = itemView.findViewById(R.id.JobFormList_serialNumber);
            tvJobId = itemView.findViewById(R.id.JobFormList_jobNumber);
            tvClientName = itemView.findViewById(R.id.JobFormList_clientName);
            tvCreated_at = itemView.findViewById(R.id.JobFormList_Date);
            invoiceCheck = itemView.findViewById(R.id.JobFormList_Invoice);

        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    createJobListFiltered = createJobList;
                } else {
                    List<JobFormModel> filteredList = new ArrayList<>();
                    for (JobFormModel row : createJobList) {

                        if (row.getCreated_at().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getClient_id_name().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    createJobListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = createJobListFiltered;
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                createJobListFiltered = (ArrayList<JobFormModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
