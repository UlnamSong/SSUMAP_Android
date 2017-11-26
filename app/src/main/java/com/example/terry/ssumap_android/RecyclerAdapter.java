package com.example.terry.ssumap_android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by terry on 2017. 11. 26..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<Facility> mItems;

    public RecyclerAdapter(ArrayList<Facility> items){
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_facility,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mNameFacility.setText(mItems.get(position).getName());
        holder.mImageFacility.setImageResource(mItems.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageFacility;
        private TextView mNameFacility;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageFacility = (ImageView) itemView.findViewById(R.id.iv_icon);
            mNameFacility = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}