package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;
/*******************************************************/
/* Created by Muhammad.Sufwan on 5/14/2018 user email ${EMAIL};. */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

import java.util.ArrayList;

/*******************************************************/
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.RecyclerViewHolder> {

    private Context mCtx;
    ArrayList<Uri> imagesUriList;

    public ImageListAdapter(Context mCtx, ArrayList<Uri> imagesUriList) {
        this.mCtx = mCtx;
        this.imagesUriList = imagesUriList;
    }


    @Override
    public ImageListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageListAdapter.RecyclerViewHolder holder, int position) {

        holder.textView.setText(String.valueOf(position));
        holder.imageView.setImageURI(imagesUriList.get(position));

    }

    @Override
    public int getItemCount() {
        return imagesUriList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.tvName);

        }
    }

}
