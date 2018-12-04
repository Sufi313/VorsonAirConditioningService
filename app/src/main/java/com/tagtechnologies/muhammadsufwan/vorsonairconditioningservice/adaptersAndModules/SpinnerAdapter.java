package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;

import java.util.List;

/**
 * Created by Muhammad.Sufwan on 2/27/2018.
 */

public class SpinnerAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<ModelNewAC> listData;
    private Context context;

    public SpinnerAdapter(Context context, List<ModelNewAC> listData) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if (convertView == null) {
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_new_ac_list_style, parent, false);
            spinnerHolder.spinnerItemList = convertView.findViewById(R.id.brandName);
            spinnerHolder.imageView = convertView.findViewById(R.id.brandImage);
            convertView.setTag(spinnerHolder);
        } else {
            spinnerHolder = (ViewHolder) convertView.getTag();
        }
        if (listData.get(position).getImage() != null && !listData.get(position).getImage().equals("")) {
            //spinnerHolder.spinnerItemList.setText(listData.get(position).getName());
            //spinnerHolder.spinnerItemList.setVisibility(View.VISIBLE);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile);
            Glide.with(context)
                    .load(Apis.BASE_URL + listData.get(position).getImage())
                    .apply(options)
                    .into(spinnerHolder.imageView);


            spinnerHolder.imageView.setVisibility(View.VISIBLE);
        } else {
            spinnerHolder.spinnerItemList.setVisibility(View.VISIBLE);
            spinnerHolder.spinnerItemList.setText(listData.get(position).getName());
        }


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if (convertView == null) {
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_new_ac_list_style, parent, false);
            spinnerHolder.spinnerItemList = convertView.findViewById(R.id.brandName);
            spinnerHolder.imageView = convertView.findViewById(R.id.brandImage);
            convertView.setTag(spinnerHolder);
        } else {
            spinnerHolder = (ViewHolder) convertView.getTag();
        }
        if (listData.get(position).getImage() != null && !listData.get(position).getImage().equals("")) {
            //spinnerHolder.spinnerItemList.setText(listData.get(position).getName());
            //spinnerHolder.spinnerItemList.setVisibility(View.VISIBLE);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile);
            Glide.with(context)
                    .load(Apis.BASE_URL + listData.get(position).getImage())
                    .apply(options)
                    .into(spinnerHolder.imageView);
            spinnerHolder.imageView.setVisibility(View.VISIBLE);
        } else {
            spinnerHolder.spinnerItemList.setVisibility(View.VISIBLE);
            spinnerHolder.spinnerItemList.setText(listData.get(position).getName());
        }


        return convertView;
    }

    class ViewHolder {
        TextView spinnerItemList;
        ImageView imageView;
    }


}
