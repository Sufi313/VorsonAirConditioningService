package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidItemInvoiceModule;

import java.util.List;

/**
 * Created by Muhammad.Sufwan on 2/27/2018.
 */

public class CreateJobItemAdapter extends RecyclerView.Adapter<CreateJobItemAdapter.RecyclerViewHolder> {

    private Context mCtx;
    private List<JobFormItemModel> getSubmitJobItems;

    public CreateJobItemAdapter(Context mCtx, List<JobFormItemModel> getSubmitJobItems) {
        this.mCtx = mCtx;
        this.getSubmitJobItems = getSubmitJobItems;
    }


    @NonNull
    @Override
    public CreateJobItemAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_create_job_list, parent, false);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CreateJobItemAdapter.RecyclerViewHolder holder, final int position) {

        final JobFormItemModel model = getSubmitJobItems.get(position);

        if (!model.getAc_no().isEmpty()) {
            holder.tvACno.setText(model.getAc_no());
        }
        if (!model.getAc_location().isEmpty()) {
            holder.tvAClocation.setText(model.getAc_location());
        }
        if (!model.getSos_filters().isEmpty()) {
            holder.layoutSOS1.setVisibility(View.VISIBLE);
            holder.tvSOS1.setText(model.getSos_filters());
        }
        if (!model.getSos_evaporator_coil().isEmpty()) {
            holder.layoutSOS2.setVisibility(View.VISIBLE);
            holder.tvSOS2.setText(model.getSos_evaporator_coil());
        }
        if (!model.getSos_blower_assambly().isEmpty()) {
            holder.layoutSOS3.setVisibility(View.VISIBLE);
            holder.tvSOS3.setText(model.getSos_blower_assambly());
        }
        if (!model.getSos_condencer_coil().isEmpty()) {
            holder.layoutSOS4.setVisibility(View.VISIBLE);
            holder.tvSOS4.setText(model.getSos_condencer_coil());
        }
        if (!model.getSos_fan_motor().isEmpty()) {
            holder.layoutSOS5.setVisibility(View.VISIBLE);
            holder.tvSOS5.setText(model.getSos_fan_motor());
        }
        if (!model.getSos_compressor().isEmpty()) {
            holder.layoutSOS6.setVisibility(View.VISIBLE);
            holder.tvSOS6.setText(model.getSos_compressor());
        }
        if (!model.getSos_line_sets_insulation().isEmpty()) {
            holder.layoutSOS7.setVisibility(View.VISIBLE);
            holder.tvSOS7.setText(model.getSos_line_sets_insulation());
        }
        if (!model.getSos_unusual_vibration().isEmpty()) {
            holder.layoutSOS8.setVisibility(View.VISIBLE);
            holder.tvSOS8.setText(model.getSos_unusual_vibration());
        }
        if (!model.getSos_capacitor_electrical().isEmpty()) {
            holder.layoutSOS9.setVisibility(View.VISIBLE);
            holder.tvSOS9.setText(model.getSos_capacitor_electrical());
        }
        if (!model.getRsp_type().isEmpty()) {
            holder.layoutRSP.setVisibility(View.VISIBLE);
            holder.tvRSP1.setText(model.getRsp_type());
        }
        if (!model.getSl_type().isEmpty()) {
            holder.layoutSL.setVisibility(View.VISIBLE);
            holder.tvSL1.setText(model.getSl_type());
        }
        if (!model.getIdu_ec_type().isEmpty()) {
            holder.layoutIDUEC.setVisibility(View.VISIBLE);
            holder.tvIDUEC1.setText(model.getIdu_ec_type());
        }
        if (!model.getOdu_cc_type().isEmpty()) {
            holder.layoutODUCC.setVisibility(View.VISIBLE);
            holder.tvODUCC1.setText(model.getOdu_cc_type());
        }
        if (!model.getPic_type().isEmpty()) {
            holder.layoutPIC.setVisibility(View.VISIBLE);
            holder.tvPIC1.setText(model.getPic_type());
        }
        if (!model.getDs_type().isEmpty()) {
            holder.layoutDS.setVisibility(View.VISIBLE);
            holder.tvDS1.setText(model.getDs_type());
        }
        if (!model.getPcs_type().isEmpty()) {
            holder.layoutPCS.setVisibility(View.VISIBLE);
            holder.tvPCS1.setText(model.getPcs_type());
        }
        if (!model.getIdu_odu_sys_type().isEmpty()) {
            holder.layoutIDUODUSYS.setVisibility(View.VISIBLE);
            holder.tvIDUODUSYS1.setText(model.getIdu_odu_sys_type());
        }

    }

    @Override
    public int getItemCount() {

        return getSubmitJobItems.size();

    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutSOS1, layoutSOS2, layoutSOS3, layoutSOS4, layoutSOS5, layoutSOS6, layoutSOS7, layoutSOS8, layoutSOS9,
                layoutRSP, layoutSL, layoutIDUEC, layoutODUCC, layoutPIC, layoutDS, layoutPCS, layoutIDUODUSYS;
        TextView tvSOS1, tvSOS2, tvSOS3, tvSOS4, tvSOS5, tvSOS6, tvSOS7, tvSOS8, tvSOS9,
                tvACno, tvAClocation, tvRSP1, tvRSP2, tvSL1, tvSL2, tvIDUEC1, tvIDUEC2, tvODUCC1, tvODUCC2, tvPIC1, tvPIC2,
                tvDS1, tvDS2, tvPCS1, tvPCS2, tvIDUODUSYS1, tvIDUODUSYS2;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            layoutSOS1 = itemView.findViewById(R.id.showSubmitFormLayoutSOS1);
            layoutSOS2 = itemView.findViewById(R.id.showSubmitFormLayoutSOS2);
            layoutSOS3 = itemView.findViewById(R.id.showSubmitFormLayoutSOS3);
            layoutSOS4 = itemView.findViewById(R.id.showSubmitFormLayoutSOS4);
            layoutSOS5 = itemView.findViewById(R.id.showSubmitFormLayoutSOS5);
            layoutSOS6 = itemView.findViewById(R.id.showSubmitFormLayoutSOS6);
            layoutSOS7 = itemView.findViewById(R.id.showSubmitFormLayoutSOS7);
            layoutSOS8 = itemView.findViewById(R.id.showSubmitFormLayoutSOS8);
            layoutSOS9 = itemView.findViewById(R.id.showSubmitFormLayoutSOS9);
            layoutRSP = itemView.findViewById(R.id.showSubmitFormLayoutRSP);
            layoutSL = itemView.findViewById(R.id.showSubmitFormLayoutSL);
            layoutIDUEC = itemView.findViewById(R.id.showSubmitFormLayoutIDUEC);
            layoutODUCC = itemView.findViewById(R.id.showSubmitFormLayoutODUCC);
            layoutPIC = itemView.findViewById(R.id.showSubmitFormLayoutPIC);
            layoutDS = itemView.findViewById(R.id.showSubmitFormLayoutDS);
            layoutPCS = itemView.findViewById(R.id.showSubmitFormLayoutPCS);
            layoutIDUODUSYS = itemView.findViewById(R.id.showSubmitFormLayoutIDUODUSYS);

            tvACno = itemView.findViewById(R.id.showSubmitFormTextACno);
            tvAClocation = itemView.findViewById(R.id.showSubmitFormTextLocation);

            tvSOS1 = itemView.findViewById(R.id.showSubmitFormTextSOS1);
            tvSOS2 = itemView.findViewById(R.id.showSubmitFormTextSOS2);
            tvSOS3 = itemView.findViewById(R.id.showSubmitFormTextSOS3);
            tvSOS4 = itemView.findViewById(R.id.showSubmitFormTextSOS4);
            tvSOS5 = itemView.findViewById(R.id.showSubmitFormTextSOS5);
            tvSOS6 = itemView.findViewById(R.id.showSubmitFormTextSOS6);
            tvSOS7 = itemView.findViewById(R.id.showSubmitFormTextSOS7);
            tvSOS8 = itemView.findViewById(R.id.showSubmitFormTextSOS8);
            tvSOS9 = itemView.findViewById(R.id.showSubmitFormTextSOS9);

            tvRSP1 = itemView.findViewById(R.id.showSubmitFormTextRSP1);
            tvRSP2 = itemView.findViewById(R.id.showSubmitFormTextRSP2);

            tvSL1 = itemView.findViewById(R.id.showSubmitFormTextSL1);
            tvSL2 = itemView.findViewById(R.id.showSubmitFormTextSL2);

            tvIDUEC1 = itemView.findViewById(R.id.showSubmitFormTextIDUEC1);
            tvIDUEC2 = itemView.findViewById(R.id.showSubmitFormTextIDUEC2);

            tvODUCC1 = itemView.findViewById(R.id.showSubmitFormTextODUCC1);
            tvODUCC2 = itemView.findViewById(R.id.showSubmitFormTextODUCC2);

            tvPIC1 = itemView.findViewById(R.id.showSubmitFormTextPIC1);
            tvPIC2 = itemView.findViewById(R.id.showSubmitFormTextPIC2);

            tvDS1 = itemView.findViewById(R.id.showSubmitFormTextDS1);
            tvDS2 = itemView.findViewById(R.id.showSubmitFormTextDS2);

            tvPCS1 = itemView.findViewById(R.id.showSubmitFormTextPCS1);
            tvPCS2 = itemView.findViewById(R.id.showSubmitFormTextPCS2);

            tvIDUODUSYS1 = itemView.findViewById(R.id.showSubmitFormTextIDUODUSYS1);
            tvIDUODUSYS2 = itemView.findViewById(R.id.showSubmitFormTextIDUODUSYS2);

        }
    }

}
