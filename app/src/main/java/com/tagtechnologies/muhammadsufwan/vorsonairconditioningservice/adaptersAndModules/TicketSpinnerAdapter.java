package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;
/*******************************************************/
/* Created by Muhammad.Sufwan on 4/21/2018 user email ${EMAIL};. */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

import java.util.List;

/*******************************************************/
public class TicketSpinnerAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<String> items;
    private Context mContext;

    public TicketSpinnerAdapter(Context mContext, List<String> items) {
        this.mContext = mContext;
        this.items = items;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder spinnerHolder;
        if (convertView == null) {
            spinnerHolder = new TicketSpinnerAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_ticket_list_layout, parent, false);
            spinnerHolder.second = convertView.findViewById(R.id.custom_spinner_text2);
            convertView.setTag(spinnerHolder);
        } else {
            spinnerHolder = (ViewHolder) convertView.getTag();
        }

        if (items.get(position).equals("Select One")) {
            spinnerHolder.second.setTextColor(mContext.getResources().getColor(R.color.primary_text));
        }

        spinnerHolder.second.setText(items.get(position));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder spinnerHolder;
        if (convertView == null) {
            spinnerHolder = new TicketSpinnerAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_ticket_list_layout, parent, false);
            spinnerHolder.second = convertView.findViewById(R.id.custom_spinner_text2);
            convertView.setTag(spinnerHolder);
        } else {
            spinnerHolder = (ViewHolder) convertView.getTag();
        }

        if (items.get(position).equals("Select One")) {
            spinnerHolder.second.setTextColor(mContext.getResources().getColor(R.color.primary_text));
        }

        spinnerHolder.second.setText(items.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView second;
    }


}
