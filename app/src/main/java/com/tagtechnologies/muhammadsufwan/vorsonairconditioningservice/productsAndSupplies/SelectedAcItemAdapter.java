package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.productsAndSupplies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidItemInvoiceModule;

import java.util.List;

/**
 * Created by Muhammad.Sufwan on 2/27/2018.
 */

public class SelectedAcItemAdapter extends RecyclerView.Adapter<SelectedAcItemAdapter.RecyclerViewHolder>{

    private Context mCtx;
    private List<AcSelectedModel> itemList;

    public SelectedAcItemAdapter(Context mCtx, List<AcSelectedModel> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }


    @Override
    public SelectedAcItemAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_selected_ac_layout, parent, false);
            return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final SelectedAcItemAdapter.RecyclerViewHolder holder, final int position) {

        final AcSelectedModel model = itemList.get(position);

        holder.tv1.setText(model.getModel());
        holder.tv2.setText(model.getQuantity());
        holder.tv3.setText(model.getFileName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        }
        return 0;
    }




    public class RecyclerViewHolder extends RecyclerView.ViewHolder  {

        TextView tv1, tv2, tv3;
        ImageView delete;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1ViewSelectedACLayout);
            tv2 = itemView.findViewById(R.id.tv2ViewSelectedACLayout);
            tv3 = itemView.findViewById(R.id.tv3ViewSelectedACLayout);
            delete = itemView.findViewById(R.id.imageView1ViewSelectedACLayout);

        }
    }



    public void removeAt(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }


}
