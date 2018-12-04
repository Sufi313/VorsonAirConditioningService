package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.details.InvoiceDetailActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * Created by Muhammad.Sufwan on 2/27/2018.
 * 
 */

public class HistoryPaidAdapter extends RecyclerView.Adapter<HistoryPaidAdapter.RecyclerViewHolder> implements Filterable {

    private Context mCtx;
    private List<HistoryPaidModule> paidInvoiceList;
    private List<HistoryPaidModule> paidInvoiceListFiltered;
    private List<HistoryPaidItemInvoiceModule> paidInvoiceItem;


    private int itemListIndex = 0;

    public HistoryPaidAdapter(Context mCtx, List<HistoryPaidModule> paidInvoiceList, List<HistoryPaidItemInvoiceModule> paidInvoiceItem) {
        this.mCtx = mCtx;
        this.paidInvoiceList = paidInvoiceList;
        this.paidInvoiceItem = paidInvoiceItem;
        this.paidInvoiceListFiltered = paidInvoiceList;
    }


    @Override
    public HistoryPaidAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_total_paid_invoices, parent, false);
            return new RecyclerViewHolder(view);
        }

    @Override
    public void onBindViewHolder(final HistoryPaidAdapter.RecyclerViewHolder holder, final int position) {

        final HistoryPaidModule module = paidInvoiceListFiltered.get(position);


        holder.tvInvoiceNO.setText(module.getInvoice_no());

        holder.tvInvoiceDate.setText(module.getCreated_at());


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DecimalFormat formatter = new DecimalFormat("#,##,###.00");
            String valueTotal = formatter.format(module.getTotal());
            holder.tvInvoiceAmount.setText(valueTotal);
            String valueSalesTax = formatter.format(module.getSales_tax());
            holder.tvInvoiceTax.setText(valueSalesTax);
            String valueSubtotal = formatter.format(module.getSubtotal());
            holder.tvInvoiceSubtotal.setText(valueSubtotal);

        } else {
            String valueTotal = mCtx.getResources().getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", module.getTotal()));
            String valueSubtotal = String.valueOf(String.format(Locale.US, "%.2f", module.getSubtotal()));
            String valueSalesTax = String.valueOf(String.format(Locale.US, "%.2f", module.getSales_tax()));
            holder.tvInvoiceAmount.setText(valueTotal);
            holder.tvInvoiceSubtotal.setText(valueSubtotal);
            holder.tvInvoiceTax.setText(valueSalesTax);
        }

        holder.tvInvoiceNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.onContentSelected(module);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (paidInvoiceListFiltered != null) {
            return paidInvoiceListFiltered.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder  {

        TextView tvInvoiceNO, tvInvoiceDate, tvInvoiceSubtotal, tvInvoiceTax, tvInvoiceAmount;

        private RecyclerViewHolder(View itemView) {
            super(itemView);

            tvInvoiceNO = itemView.findViewById(R.id.invoiceNO_invoice_list);
            tvInvoiceDate = itemView.findViewById(R.id.date_invoice_list);
            tvInvoiceSubtotal = itemView.findViewById(R.id.subtotal_invoice_list);
            tvInvoiceTax = itemView.findViewById(R.id.tax_invoice_list);
            tvInvoiceAmount = itemView.findViewById(R.id.amount_invoice_list);

        }


        public void onContentSelected(HistoryPaidModule model) {

            Intent i = new Intent(mCtx, InvoiceDetailActivity.class);
            i.putExtra("invoice_id", model.getId());
            i.putExtra("invoice_no", model.getInvoice_no());
            i.putExtra("invoice_date", model.getCreated_at());
            i.putExtra("invoice_dn", model.getDn_no());
            i.putExtra("invoice_SNTN", model.getSntn_no());
            i.putExtra("invoice_STRN", model.getStran_no());
            i.putExtra("invoice_nameAddress", model.getName_address());
            i.putExtra("invoice_branchAddress", model.getBranch_address());
            i.putExtra("invoice_subtotal", model.getSubtotal());
            i.putExtra("invoice_salesTax", model.getSales_tax());
            i.putExtra("invoice_total", model.getTotal());
            i.putExtra("invoice_advance", model.getAdvance());
            i.putExtra("invoice_balanceDue", model.getBalance_due());
            i.putExtra("invoice_type", model.getInvoice_type());
            i.putExtra("invoice_status", model.getStatus());
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
                    paidInvoiceListFiltered = paidInvoiceList;
                } else {
                    List<HistoryPaidModule> filteredList = new ArrayList<>();
                    for (HistoryPaidModule row : paidInvoiceList) {


                        if (row.getInvoice_no().toLowerCase().contains(charString.toLowerCase()) || row.getCreated_at().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    paidInvoiceListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = paidInvoiceListFiltered;
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                paidInvoiceListFiltered = (ArrayList<HistoryPaidModule>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

}
