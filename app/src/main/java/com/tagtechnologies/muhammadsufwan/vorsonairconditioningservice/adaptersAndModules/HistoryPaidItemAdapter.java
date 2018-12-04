package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

import java.util.List;

/**
 * Created by Muhammad.Sufwan on 2/27/2018.
 */

public class HistoryPaidItemAdapter extends RecyclerView.Adapter<HistoryPaidItemAdapter.RecyclerViewHolder>{

    private Context mCtx;
    private List<HistoryPaidItemInvoiceModule> paidInvoiceItem;

    public HistoryPaidItemAdapter(Context mCtx, List<HistoryPaidItemInvoiceModule> paidInvoiceItem) {
        this.mCtx = mCtx;
        this.paidInvoiceItem = paidInvoiceItem;
    }


    @Override
    public HistoryPaidItemAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_item_list, parent, false);
            return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final HistoryPaidItemAdapter.RecyclerViewHolder holder, final int position) {

        final HistoryPaidItemInvoiceModule model = paidInvoiceItem.get(position);

        holder.tvItemDesc.setText(model.getDescription());
        holder.tvItemQuant.setText(model.getQuantity());
        holder.tvItemRate.setText(model.getRate());
        holder.tvItemAmount.setText(model.getAmount());


    }

    @Override
    public int getItemCount() {
        if (paidInvoiceItem != null) {
            return paidInvoiceItem.size();
        }
        return 0;
    }




    public class RecyclerViewHolder extends RecyclerView.ViewHolder  {

        TextView tvItemDesc, tvItemQuant, tvItemRate, tvItemAmount;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tvItemDesc = itemView.findViewById(R.id.showItemDescription);
            tvItemQuant = itemView.findViewById(R.id.showItemQuantity);
            tvItemRate = itemView.findViewById(R.id.showItemRate);
            tvItemAmount = itemView.findViewById(R.id.showItemAmount);

        }
    }

}
