package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.TicketDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad.Sufwan on 2/27/2018.
 */

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.RecyclerViewHolder> implements Filterable {

    private Context mCtx;
    private List<TicketModule> ticketList;
    private List<TicketModule> ticketListFiltered;


    public TicketListAdapter(Context mCtx, List<TicketModule> ticketList) {
        this.mCtx = mCtx;
        this.ticketList = ticketList;
        this.ticketListFiltered = ticketList;
    }


    @NonNull
    @Override
    public TicketListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TicketListAdapter.RecyclerViewHolder holder, final int position) {

        final TicketModule module = ticketListFiltered.get(position);

        holder.tvsrNO.setText(String.valueOf(position+1));
        holder.tvcompNo.setText(String.valueOf(module.getId()));
        holder.tvDate.setText(module.getCreated_at());
        holder.tvService.setText(module.getHelp());
        if (module.getStatus() == 0) {
            holder.tvStatus.setText("Pending");
        }
        if (module.getStatus() == 1) {
            holder.tvStatus.setText("In Progress");
        }
        if (module.getStatus() == 2) {
            holder.tvStatus.setText("Completed");
        }

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onContentSelected(module);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (ticketListFiltered != null) {
            return ticketListFiltered.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvsrNO, tvcompNo, tvDate, tvService, tvStatus;
        LinearLayout main;

        private RecyclerViewHolder(View itemView) {
            super(itemView);

            tvsrNO = itemView.findViewById(R.id.srNoTicketList);
            tvcompNo = itemView.findViewById(R.id.compNoTicketList);
            tvDate = itemView.findViewById(R.id.dateTicketList);
            tvService = itemView.findViewById(R.id.serviceTicketList);
            tvStatus = itemView.findViewById(R.id.statusTicketList);
            main = itemView.findViewById(R.id.mainLayoutTicketList);

        }


        public void onContentSelected(TicketModule model) {

            Intent i = new Intent(mCtx, TicketDetailActivity.class);
            if (model.getStatus() == 0) {
                i.putExtra("id", model.getId());
                i.putExtra("help", model.getHelp());
                i.putExtra("message", model.getMessage());
                i.putExtra("date", model.getCreated_at());
                i.putExtra("status", model.getStatus());
            }
            if (model.getStatus() == 1) {
                i.putExtra("id", model.getId());
                i.putExtra("help", model.getHelp());
                i.putExtra("message", model.getMessage());
                i.putExtra("date", model.getCreated_at());
                i.putExtra("status", model.getStatus());
                i.putExtra("tech_name", model.getTech_name());
                i.putExtra("number", model.getTech_number());
            }
            if (model.getStatus() == 2) {
                i.putExtra("id", model.getId());
                i.putExtra("help", model.getHelp());
                i.putExtra("message", model.getMessage());
                i.putExtra("date", model.getCreated_at());
                i.putExtra("status", model.getStatus());
                i.putExtra("tech_name", model.getTech_name());
                i.putExtra("number", model.getTech_number());
                i.putExtra("task_end_date", model.getTask_ended_at());
                i.putExtra("invoice_id", model.getInvoice_id());
            }
            mCtx.startActivity(i);

        }


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ticketListFiltered = ticketList;
                } else {
                    List<TicketModule> filteredList = new ArrayList<>();
                    for (TicketModule row : ticketList) {


//                        if (row.getId().contains(Integer.parse(parcharString.toLowerCase())) || row.getCreated_at().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
                    }

                    ticketListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ticketListFiltered;
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                ticketListFiltered = (ArrayList<TicketModule>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

}
